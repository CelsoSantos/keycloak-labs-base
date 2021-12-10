package com.mycompany.auth.provider.user;

import com.mycompany.auth.provider.dto.JwtContentDto;
import com.mycompany.auth.provider.dto.RecordDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomOIDCProtocolMapper extends AbstractOIDCProtocolMapper
    implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

  public static final String PROVIDER_ID = "oidc-customprotocolmapper";

  private static final List<ProviderConfigProperty> configProperties =
      new ArrayList<>();

  private static final Logger log = LoggerFactory.getLogger(CustomOIDCProtocolMapper.class);

  static {

//    ProviderConfigProperty property;
//
//    property = new ProviderConfigProperty();
//    property.setName(ProtocolMapperUtils.MULTIVALUED);
//    property.setLabel(ProtocolMapperUtils.MULTIVALUED_LABEL);
//    property.setHelpText(ProtocolMapperUtils.MULTIVALUED_HELP_TEXT);
//    property.setType(ProviderConfigProperty.BOOLEAN_TYPE);
//    configProperties.add(property);
    OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
    OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties,
        CustomOIDCProtocolMapper.class);
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {

    return configProperties;
  }

  @Override
  public String getDisplayCategory() {

    return TOKEN_MAPPER_CATEGORY;
  }

  @Override
  public String getDisplayType() {

    return "Custom Protocol Mapper";
  }

  @Override
  public String getId() {
    log.info("SOAM: inside getId");

    return PROVIDER_ID;
  }

  @Override
  public String getHelpText() {
    log.info("SOAM: inside getHelpText");

    return "some help text";
  }

  @Override
  public IDToken transformIDToken(IDToken token, ProtocolMapperModel mappingModel, KeycloakSession session, UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
    log.info("SOAM: inside transformIDToken");
//    if (!OIDCAttributeMapperHelper.includeInIDToken(mappingModel)) {
      IDToken augmentedToken = setIdTokenNameClaims(token);
      setClaim(augmentedToken, mappingModel, userSession, session, clientSessionCtx);
      return augmentedToken;
//    } else {
//      return token;
//    }
  }

  @Override
  public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel,
      KeycloakSession session, UserSessionModel userSession,
      ClientSessionContext clientSessionCtx) {
    log.info("SOAM: inside transformAccessToken");

    CustomUserStorageProvider storageProvider = new CustomUserStorageProvider(session);
    AccessToken augmentedToken = putIntoToken(storageProvider, token, userSession);

    setClaim(augmentedToken, mappingModel, userSession, session, clientSessionCtx);
    return augmentedToken;
  }

  private IDToken setIdTokenNameClaims(IDToken token) {

    JwtContentDto additionalContent = CustomUserStorageProvider.getJwtAdditionalContent();
    RecordDto userInfo = additionalContent.getUserInfo();

    token.setFamilyName(userInfo.getUserLastName());
    token.setGivenName(userInfo.getUserFirstName());
    token.setName(userInfo.getUserFullName());
    return token;
  }
  
  private AccessToken putIntoToken(CustomUserStorageProvider provider, AccessToken token, UserSessionModel userSession) {

//    JwtAdditionalContentDto additionalContent = provider.getJwtAdditionalContent();
    JwtContentDto additionalContent = CustomUserStorageProvider.getJwtAdditionalContent();
    RecordDto userInfo = additionalContent.getUserInfo();

    token.getOtherClaims().put("https://mycompany.com/parent_token", additionalContent.getParentToken().orElse(""));
    token.getOtherClaims().put("https://mycompany.com/name", userInfo.getUserFullName());
    token.getOtherClaims().put("https://mycompany.com/email", userInfo.getUserEmail());
    if (userInfo.getUserPhoneNumber().isPresent())
      token.getOtherClaims().put("https://mycompany.com/phone_number", userInfo.getUserPhoneNumber().orElse(""));
    token.getOtherClaims().put("https://mycompany.com/user_roles", userInfo.getUserRoles());
    token.getOtherClaims().put("https://mycompany.com/zoneinfo", "UTC");
    token.getOtherClaims().put("https://mycompany.com/organization_id", System.getenv("ORGANIZATION_ID"));

    token.setSubject(userInfo.getId());
    token.addAudience(System.getenv("AUDIENCE"));

    return token;
  }

  public static ProtocolMapperModel create(String name, boolean accessToken, boolean idToken,
      boolean userInfo, String tokenClaimName) {
//  public static ProtocolMapperModel create(boolean accessToken, boolean idToken,
//    boolean userInfo, String tokenClaimName) {

    ProtocolMapperModel model = new ProtocolMapperModel();
    model.setName(name);
    model.setProtocolMapper(PROVIDER_ID);
    model.setProtocol(OIDCLoginProtocol.LOGIN_PROTOCOL);
    Map<String, String> config = new HashMap<>();
    config.put(OIDCAttributeMapperHelper.TOKEN_CLAIM_NAME, tokenClaimName);
    config.put(OIDCAttributeMapperHelper.INCLUDE_IN_ACCESS_TOKEN, Boolean.toString(accessToken));
    config.put(OIDCAttributeMapperHelper.INCLUDE_IN_ID_TOKEN, Boolean.toString(idToken));
    config.put(OIDCAttributeMapperHelper.INCLUDE_IN_USERINFO, Boolean.toString(userInfo));
    model.setConfig(config);
    return model;
  }

}
