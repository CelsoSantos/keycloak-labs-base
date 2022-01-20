package com.mydomain.auth.provider.user;

import com.mydomain.auth.provider.dto.LoginResponseDto;
import com.mydomain.auth.provider.dto.RecordDto;
import com.mydomain.auth.provider.dto.RestClient;
import com.mydomain.auth.provider.dto.UserInfoDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.WebApplicationException;
import lombok.Setter;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUserStorageProvider implements
		UserStorageProvider,
		UserLookupProvider,
		CredentialInputValidator {//,
		//UserQueryProvider {,
		//UserRegistrationProvider,

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);
	protected Map<String, UserModel> loadedUsers = new HashMap<>();

	@Setter
	private ComponentModel model;

	@Setter
	private KeycloakSession ksession;

	private RestClient client;

	// UserStorageProvider

	public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model) {
		this.ksession = ksession;
		this.model = model;
		this.client = new RestClient();
	}

	public CustomUserStorageProvider(KeycloakSession session) {
		this.ksession = session;
	}

	@Override
	public void close() {
		log.info("[I30] close()");
	}

	// UserLookupProvider

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		log.info("[I35] getUserById({})", id);

		return getUserByUsername(StorageId.externalId(id), realm);
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		log.info("[I41] getUserByUsername({})", username);
		return findUser(realm, username);
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		log.info("[I48] getUserByEmail({})", email);

		return findUser(realm, email);
	}

	// CredentialInputValidator

	@Override
	public boolean supportsCredentialType(String credentialType) {
		log.info("[I57] supportsCredentialType({})", credentialType);
		return credentialType.equals(PasswordCredentialModel.TYPE);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		log.info("[I57] isConfiguredFor(realm={},user={},credentialType={})", realm.getName(), user.getUsername(),
				credentialType);
		return supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
		log.info("[I57] isValid(realm={},user={},credentialInput.type={})", realm.getName(), user.getUsername(),
				credentialInput.getType());

		boolean isSuccessfulLogedIn = false;

		try {

			String amxToken;
			// Login into Third-Party IDP with the data inserted in the form
			String responseBody = client.login(user.getUsername(), credentialInput.getChallengeResponse());
			LoginResponseDto loginBodyResponse = client.mapLoginResponse(responseBody);
			// If OK, we get a JSESSION cookie/token from AMX
			amxToken = loginBodyResponse.getTokenId();

			if (!supportsCredentialType(credentialInput.getType())) {
				return false;
			}

			if (amxToken != null && !amxToken.isEmpty()) {

				// Now that we have a JSESSION, we can use it to request the current-user data
				String userInfo = client.getUserInfo(amxToken);
				UserInfoDto mappedUser = client.mapUserInfo(userInfo);

				// This is the actual UserRecord, with user information.
				// We just use a subset of the returned data
				RecordDto userRecord = mappedUser.getRecords().get(0);

				// Clean the "temporary" record with just the user email
				UserModel adapter = findUser(realm, user.getEmail());
				if (adapter != null) {
					loadedUsers.remove(adapter);
//					ksession.userLocalStorage().removeUser(realm, adapter);
				}

				//UserModel newAdapter = new ReadOnlyUserAdapter(ksession, realm, model, userRecord);

				// Now that we have the userData with all information, set the model with all properties and attributes
				//TODO: For some reason, looks like the username is not being properly set by the JSON mapping.
				userRecord.setUsername(userRecord.getEmail()); // Set the username so the adapter doesn't fail
				UserModel newAdapter = new FederatedUserAdapter(ksession, realm, model, userRecord);
				loadedUsers.put(user.getUsername(), newAdapter);
//				ksession.userLocalStorage().addUser(realm, newAdapter);

				isSuccessfulLogedIn = true;
			} else {
				log.info("isValid: returning false");
				return false;
			}
		} catch (IOException e) {
			log.error("Error In isValid", e);
		}

		return isSuccessfulLogedIn;
	}

	// UserRegistrationProvider

/*	@Override
	public UserModel addUser(RealmModel realm, String username) {
		logger.warn("Adding users is not supported");
		return null;
	}

	@Override
	public boolean removeUser(RealmModel realm, UserModel user) {
		logger.warn("Removing users is not supported");
		return false;
	}*/

	// UserQueryProvider

/*	@Override
	public int getUsersCount(RealmModel realm) {
		log.info("[I93] getUsersCount: realm={}", realm.getName());
		return 0;
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm) {
		return getUsers(realm, 0, 5000); // Keep a reasonable maxResults
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
		log.info("[I113] getUsers: realm={}", realm.getName());
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm) {
		return searchForUser(search, realm, 0, 5000);
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
		log.info("[I139] searchForUser: realm={}", realm.getName());
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
		return searchForUser(params, realm, 0, 5000);
	}

	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
			int maxResults) {
		return getUsers(realm, firstResult, maxResults);
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
		return Collections.emptyList();
	}*/

	// Local utility methods

	private UserModel findUser(RealmModel realm, String identifier) {
		UserModel adapter = loadedUsers.get(identifier);

		if (adapter == null) {
			try {
				// Create a "temporary" userModel with just the email, I don't have any more information at the moment
				//adapter = new ReadOnlyUserAdapter(ksession, realm, model, identifier);
				adapter = new FederatedUserAdapter(ksession, realm, model, identifier);
				loadedUsers.put(identifier, adapter);
			} catch (WebApplicationException e) {
				log.warn("User with identifier '%s' could not be found, response from server: %s", identifier,
						e.getResponse().getStatus());
			}
		} else {
			log.debug("Found user data for %s in loadedUsers.", identifier);
		}

		return adapter;
	}
}
