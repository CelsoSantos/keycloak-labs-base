package com.mydomain.auth.provider.user;

import com.mydomain.auth.provider.dto.LoginResponseDto;
import com.mydomain.auth.provider.dto.RecordDto;
import com.mydomain.auth.provider.dto.RestClient;
import com.mydomain.auth.provider.dto.UserInfoDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
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
		CredentialInputValidator {

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);

	protected Map<String, UserModel> loadedUsers = new HashMap<>();

	@Setter
	private ComponentModel model;

	@Setter
	private KeycloakSession ksession;

	private RestClient client;

	private UserModelFactory userModelFactory;

	// UserStorageProvider

	public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model, UserModelFactory factory) {
		this.ksession = ksession;
		this.model = model;
		this.userModelFactory = factory;
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

		if (!supportsCredentialType(credentialInput.getType())) {
			return false;
		}

		boolean isSuccessfulLogedIn = false;

		try {

			String amxToken;
			// Login into Third-Party IDP with the data inserted in the form
			String responseBody = client.login(user.getUsername(), credentialInput.getChallengeResponse());
			LoginResponseDto loginBodyResponse = client.mapLoginResponse(responseBody);
			// If OK, we get a JSESSION cookie/token issued by Third-Party IDP
			amxToken = loginBodyResponse.getTokenId();

			if (amxToken != null && !amxToken.isEmpty()) {

				// Now that we have a JSESSION, we can use it to request the current-user data
				String userInfo = client.getUserInfo(amxToken);
				UserInfoDto mappedUser = client.mapUserInfo(userInfo);

				// This is the actual UserRecord, with user information.
				// We just use a subset of the returned data
				RecordDto userRecord = mappedUser.getRecords().get(0);

				// Clean the "temporary" record with just the user email
				// TODO: Can I maybe "fork" behaviour depending on the type of the object originating the UserModel?
				log.info("[I9] Checking if temporary user exists..");
				UserModel adapter = findUser(realm, user.getUsername());
				if (adapter != null) {
					log.info("[I10] Removing id={}; username={} from adapter", adapter.getId(), adapter.getUsername());
					log.info("[I11] Adapter type: {}", adapter.getClass());
					loadedUsers.remove(adapter.getId());
//					ksession.userCache().clear();
				}

				// Now that we have the userData with all information, set the model with all properties and attributes
				// TODO: For some reason, looks like the username is not being properly set by the JSON mapping.
				userRecord.setUsername(userRecord.getEmail()); // Set the username so the adapter doesn't fail
				UserModel newAdapter = userModelFactory.create(realm, userRecord);
//				UserModel newAdapter = new FederatedUserAdapter(ksession, realm, model, userRecord);
				loadedUsers.put(user.getUsername(), newAdapter);
				if (null != newAdapter)
					ksession.userCache().clear();

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

	// Local utility methods

	private UserModel findUser(RealmModel realm, String identifier) {
		log.info("[I69] findUser(identifier={})", identifier);

		// See if the user is already loaded in-memory
		log.info("Loaded Users: {}", loadedUsers.values().stream().map(Object::toString).collect(Collectors.joining(";")));
		UserModel adapter = loadedUsers.get(identifier);

		// If not, then let's see if we have a stored user with this identifier
		if (adapter == null) {
			log.info("[I70] no user in loadedusers.. getting from localstorage...");
			adapter = ksession.userLocalStorage().getUserById(realm, identifier);
			// Put user in the loaded users in-memory
			loadedUsers.put(identifier, adapter);
		}

		// If there is no stored user, then it must be a first login, we need a ReadOnlyAdapter
		if (adapter == null) {
			try {
				log.info("[I71] no user in localstorage.. creating ReadOnlyAdapter");
				// Create a "temporary" userModel with just the email
				adapter = new ReadOnlyUserAdapter(ksession, realm, model, identifier);
//				adapter = new FederatedUserAdapter(ksession, realm, model, identifier);
				loadedUsers.put(identifier, adapter);
			} catch (WebApplicationException e) {
				log.warn("User with identifier '{}' could not be found, response from server: {}", identifier,
						e.getResponse().getStatus());
			}
		} else {
			log.debug("Found user data for {} in loadedUsers.", identifier);
		}

		return adapter;
	}
}
