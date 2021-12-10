package com.mycompany.auth.provider.user;

import com.mycompany.auth.provider.dto.AdditionalInfoDto;
import com.mycompany.auth.provider.dto.JwtContentDto;
import com.mycompany.auth.provider.dto.RecordDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.auth.provider.dto.LoginResponseDto;
import com.mycompany.auth.provider.dto.UserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class CustomUserStorageProvider
		implements UserStorageProvider, UserLookupProvider, CredentialInputValidator, UserQueryProvider {

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);
	protected Map<String, UserModel> loadedUsers = new HashMap<>();

	private ComponentModel model;
	private KeycloakSession ksession;
//	private JwtAdditionalContentDto jwtAdditionalContent;
	private static final JwtContentDto jwtAdditionalContent = new JwtContentDto();

	public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model) {
		this.ksession = ksession;
		this.model = model;
	}

	public CustomUserStorageProvider(KeycloakSession session) {
		this.ksession = session;
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		log.info("[I35] getUserById({})", id);

		StorageId storageId = new StorageId(id);
		String username = storageId.getExternalId();
		return getUserByUsername(username, realm);
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		log.info("[I41] getUserByUsername({})", username);

		UserModel adapter = loadedUsers.get(username);

		if (adapter == null) {
			adapter = createAdapter(realm, username);
			loadedUsers.put(username, adapter);
		}
		return adapter;
	}

	protected UserModel createAdapter(RealmModel realm, final String email) {
		return new AbstractUserAdapter(ksession, realm, model) {

			@Override
			public String getUsername() {
				return email;
			}
		};
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		log.info("[I48] getUserByEmail({})", email);

		UserModel adapter = loadedUsers.get(email);
		if (adapter == null) {
			adapter = createAdapter(realm, email);
			loadedUsers.put(email, adapter);
		}
		return adapter;
	}

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

			String loginPath = System.getenv("ORIGINAL_BASE") + "/moik/ext/login/login";
			String responseBody = login(loginPath, user.getUsername(), credentialInput.getChallengeResponse());
			LoginResponseDto loginBodyResponse = mapLoginResponse(responseBody);
			String originalToken = loginBodyResponse.getTokenId();

			if (!supportsCredentialType(credentialInput.getType())) {
				return false;
			}

			if (originalToken != null && !originalToken.isEmpty()) {
//				jwtAdditionalContent = new JwtAdditionalContentDto();
				String userInfo = getUserInfo(Optional.of(originalToken).orElse(""));
				UserInfoDto mappedUser = mapUserInfo(userInfo);

				RecordDto userRecord = mappedUser.getRecords().get(0);
				String additionalInfo = userRecord.getUserAdditionalInfo();

				jwtAdditionalContent.setUserInfo(userRecord);
				jwtAdditionalContent.setParentToken(Optional.of(originalToken));
				jwtAdditionalContent.setCircleContent(additionalInfo);
				addCircleRolesToUserRoles(additionalInfo);
//				addCircleRolesToUserRoles(additionalInfo, jwtAdditionalContent);

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

	private void addCircleRolesToUserRoles(String actionableContent) {
//	private void addCircleRolesToUserRoles(String actionableContent, JwtAdditionalContentDto jwtAdditionalContent) {

		ObjectMapper mapper = new ObjectMapper();
		AdditionalInfoDto circle;

		try {
			circle = mapper.readValue(actionableContent, AdditionalInfoDto.class);
			List<String> roles = jwtAdditionalContent.getUserInfo().getUserRoles();
			if (circle.isActionableContent()) {
				roles.add("ac-consumer");
			}
			if(circle.isMipIndicative()) {
				roles.add("mip-indicative");
			}
		} catch (JsonProcessingException e) {
			log.error("[ERROR] addActionableContentToUserRoles", e);
			e.printStackTrace();
		}
	}

	private LoginResponseDto mapLoginResponse(String loginResponse) {

		ObjectMapper mapper = new ObjectMapper();
		LoginResponseDto loginBodyResponse = new LoginResponseDto();

		try {
			loginBodyResponse = mapper.readValue(loginResponse, LoginResponseDto.class);
			return loginBodyResponse;
		} catch (JsonProcessingException e) {
			log.error("[ERROR] mapLoginResponse", e);
			e.printStackTrace();
		}

		return loginBodyResponse;
	}

	private String login(String url, String email, String password) throws IOException {

		HttpPost post = new HttpPost(url);
		// add request parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("auth", "emailpassword"));
		urlParameters.add(new BasicNameValuePair("email", email));
		urlParameters.add(new BasicNameValuePair("password", password));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			return EntityUtils.toString(response.getEntity());
		}
	}

	private String getUserInfo(String originalToken) {

		String currentUserPath = System.getenv("ORIGINAL_BASE") + "/moik/ext/auth/current-user";

		HttpGet get = new HttpGet(currentUserPath);
		HttpContext localContext = getHttpContext(originalToken);
		String userInfo = "";

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse response = client.execute(get, localContext);
			userInfo = EntityUtils.toString(response.getEntity());
			return userInfo;
		} catch (IOException e) {
			log.error("Error in getUserInfo: ", e);
		}

		return userInfo;
	}

	private HttpContext getHttpContext(String originalToken) {

		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, setCookies(originalToken));

		return localContext;
	}

	private BasicCookieStore setCookies(String originalToken) {

		String domain = System.getenv("DOMAIN");

		BasicClientCookie cookie = new BasicClientCookie("tokenId", originalToken);
		cookie.setDomain(domain);
		cookie.setPath("/moik");
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(cookie);

		return cookieStore;
	}

	private UserInfoDto mapUserInfo(String userInfo) {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jdk8Module());
		UserInfoDto userInfoMapped = new UserInfoDto();

		try {
			userInfoMapped = mapper.readValue(userInfo, UserInfoDto.class);
			return userInfoMapped;
		} catch (JsonProcessingException e) {
			log.error("[ERROR] mapUserInfo", e);
			e.printStackTrace();
		}

		return userInfoMapped;
	}

	@Override
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
	}

	@Override
	public void close() {
		log.info("[I30] close()");
	}

	public static JwtContentDto getJwtAdditionalContent() {
		return jwtAdditionalContent;
	}
}
