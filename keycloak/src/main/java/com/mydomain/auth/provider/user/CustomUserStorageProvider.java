package com.mydomain.auth.provider.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.auth.provider.dto.CircleDto;
import com.mydomain.auth.provider.dto.LoginResponseDto;
import com.mydomain.auth.provider.dto.RecordDto;
import com.mydomain.auth.provider.dto.RestClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.WebApplicationException;
import lombok.Setter;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUserStorageProvider implements
		UserStorageProvider,
		UserLookupProvider,
		CredentialInputValidator,
		CredentialInputUpdater {

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);

	@Setter
	private ComponentModel model;

	@Setter
	private KeycloakSession ksession;

	private RestClient client;

	// UserStorageProvider

	public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model) { //, UserModelFactory factory) {
		this.ksession = ksession;
		this.model = model;
		// this.userModelFactory = factory;
		this.client = new RestClient();
	}

	public CustomUserStorageProvider(KeycloakSession session) {
		this.ksession = session;
	}

	@Override
	public void close() {
		log.info("[I99] close()");
	}

	// UserLookupProvider

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		log.info("[I01] getUserById(id={}, realm={})", id, realm.getName());

		return findUser(realm, id);
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		log.info("[I02] getUserByUsername(username={}, realm={})", username, realm.getName());
		return getUserByEmail(realm, username);
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		log.info("[I03] getUserByEmail(email={}, realm={})", email, realm.getName());

		return findUser(realm, email);
	}

	// CredentialInputUpdater

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		log.info("[I60] updateCredential(realm={}, user=id:{}[email:{}])", realm.getName(), user.getId(), user.getEmail());
		if (PasswordCredentialModel.TYPE.equals(input.getType())) {
			throw new ReadOnlyException("User is read only");
		}
		return false;
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
		log.info("[I61] disableCredentialType(realm={}, user=id:{}[email:{}], credentialType={})", realm.getName(), user.getId(), user.getEmail(), credentialType);
	}

	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
		log.info("[I62] getDisableableCredentialTypes(realm={}, user=id:{}[email:{}])", realm.getName(), user.getId(), user.getEmail());
		return new HashSet<>();
	}

	// CredentialInputValidator

	@Override
	public boolean supportsCredentialType(String credentialType) {
		log.info("[I07] supportsCredentialType({})", credentialType);
		return credentialType.equals(PasswordCredentialModel.TYPE);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		log.info("[I08] isConfiguredFor(realm={}, user=id:{}[email:{}], credentialType={})", realm.getName(), user.getId(), user.getUsername(),
				credentialType);
		return supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
		log.info("[I09] isValid(realm={}, user=id:{}[email:{}], credentialInput.type={})", realm.getName(), user.getId(), user.getUsername(),
				credentialInput.getType());

		boolean isSuccessfulLogedIn = false;

		try {

			String amxToken;
			// Login into Third-Party IDP with the data inserted in the form
			LoginResponseDto loginBodyResponse = client.login(user.getUsername(), credentialInput.getChallengeResponse());

			// If OK, we get a JSESSION cookie/token issued by Third-Party IDP
			amxToken = loginBodyResponse.getTokenId();

			if (amxToken != null && !amxToken.isEmpty()) {
				// Now that we have a JSESSION, we can use it to request the current-user data
				// This is the actual UserRecord, with user information.
				// We just use a subset of the returned data
				RecordDto userRecord = client.getUserInfo(amxToken);

				// TODO: For some reason, looks like the username is not being properly set by the JSON mapping.
				userRecord.setUsername(userRecord.getEmail()); // Set the username so the adapter doesn't fail

				// Now that we have the userData with all information, set the model with all properties and attributes
				updateUserInfo(realm, userRecord);

				log.info("[I09] Cleaning session userCache...");
				ksession.userCache().clear();

				isSuccessfulLogedIn = true;
			} else {
				log.info("[I09] isValid: returning false");
				return false;
			}
		} catch (IOException e) {
			log.error("[I09] Error In isValid", e);
		}

		log.info("[I09] User Validated? {}", isSuccessfulLogedIn);
		return isSuccessfulLogedIn;
	}

	// Local utility methods

	private UserModel findUser(RealmModel realm, String identifier) {
		log.info("[I04] findUser(realm={}, identifier={})", realm.getName(), identifier);

		UserModel adapter = getFromStorage(realm, identifier);

		if (adapter == null) {
			try {
				log.info("[I04] no user in userLocalStorage...");
				log.info("[I04] creating initial UserModel...");
				// Create a "initial" userModel with just the email
				// This happens the very first time a user logs-in and we need to create it
				adapter = addToStorage(realm, identifier);
			} catch (WebApplicationException e) {
				log.warn("[I04] User with identifier '{}' could not be found, response from server: {}", identifier,
						e.getResponse().getStatus());
			}
		} else {
			log.debug("[I04] Found user data for user [{}] in userLocalStorage.", identifier);
		}

		return adapter;
	}

	private UserModel getFromStorage(RealmModel realm, String identifier) {
		log.info("[I05] getFromStorage(realm={}, identifier={})", realm.getId(), identifier);
		log.info("[I05] Trying to find user [{}] in userLocalStorage...", identifier);

		log.info("[I05] Users: \n" + ksession.userCache().getUsersStream(realm).map(u -> "ID: " + u.getId() + " - Email: " + u.getEmail()).collect(Collectors.toList()));

		return ksession.userLocalStorage().getUserByEmail(realm, identifier);
	}

	private UserModel addToStorage(RealmModel realm, String identifier) {
		log.info("[I06] addToStorage(realm={}, identifier={})", realm.getId(), identifier);
		UserModel adapter = ksession.userLocalStorage().addUser(
				realm,
				UUID.randomUUID().toString(),
				identifier,
				true,
				false);

		log.info("[I06] Setting Federation link and email status...");
		log.info("[I06] Model ID: {}", model.getId());
		adapter.setFederationLink(model.getId());
		adapter.setEnabled(true);
		adapter.setEmailVerified(true);
		adapter.setSingleAttribute(UserModel.EMAIL, identifier);
		adapter.setSingleAttribute(UserModel.USERNAME, identifier);

		return adapter;
	}

	private UserModel updateUserInfo(RealmModel realm, UserDetails user) {
		log.info("[I10] updateUserInfo(realm={}, user=id:{}[email:{}])", realm.getId(), user.getId(), user.getEmail());
		UserModel local = ksession.userCache().getUserByEmail(realm, user.getEmail());

		if (local == null)
			local = getFromStorage(realm, user.getEmail());

		if (local == null) {
			log.info("[I10] User not found in userLocalStorage...");
			log.info("[I10] Adding to userLocalStorage...");
			local = addToStorage(realm, user.getEmail());
		}

		log.info("[I10] Setting attributes...");
		setUserAttributes(local, user);

		if (user.getAdditionalInfo() != null) {
			String additionalInfo = user.getAdditionalInfo();

			log.info("[I10] Setting roles...");
			getRoleModels(realm, additionalInfo)
					.forEach(local::grantRole);
		}

		log.info("[I10] Users Cache: \n" +
				ksession.userCache()
						.getUsersStream(realm)
						.map(u -> "ID: " + u.getId() + " - Email: " + u.getEmail())
						.collect(Collectors.toList()));

		return local;
	}

	private UserModel setUserAttributes(UserModel model, UserDetails user) {
		log.info("[I11] setUserAttributes(model=id:{}[email:{}], user=id:{}[email:{}])", model.getId(), model.getUsername(), user.getId(), user.getEmail());
		model.setSingleAttribute(UserModel.FIRST_NAME, user.getFirstName());
		model.setSingleAttribute(UserModel.LAST_NAME, user.getLastName());

		model.setSingleAttribute("originalId", user.getId());
		model.setSingleAttribute("parentToken", user.getSessionId());
		model.setSingleAttribute("name", user.getFullName());
		model.setSingleAttribute("phoneNumber", user.getPhoneNumber());
		model.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));
		return model;
	}

	public Stream<RoleModel> getRoleModels(RealmModel realm, String additionalInfo) {
		log.info("[I12] getRoleModels(realm={}, additionalInfo={})", realm.getName(), additionalInfo);
		ObjectMapper mapper = new ObjectMapper();
		CircleDto circle;

		List<String> roles = new ArrayList<>();
		try {
			circle = mapper.readValue(additionalInfo, CircleDto.class);

			if (circle != null && circle.isActionableContent())
				roles.add("ac-consumer");

			if (circle != null && circle.isMipIndicative())
				roles.add("mip-indicative");

			if (circle != null && circle.isMipFull())
				roles.add("mip-full");

			if(circle != null && circle.isLcmBasic())
				roles.add("lcm-basic");

			if(circle != null && circle.isLcmFull())
				roles.add("lcm-full");

		} catch (JsonProcessingException e) {
			log.error("[ERROR] roleMapping", e);
			e.printStackTrace();
		}

		return roles.stream()
				.map(r -> getRoleModel(realm, r))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	private Optional<RoleModel> getRoleModel(RealmModel realm, String role) {
		log.info("[I13] getRoleModel(realm={}, role={})", realm.getName(), role);
		return Optional.ofNullable(realm.getRole(role))
				.or(() -> {
					log.debug(String.format("Added role %s to realm %s", role, realm.getName()));
					return Optional.ofNullable(realm.addRole(role));
				});
	}
}
