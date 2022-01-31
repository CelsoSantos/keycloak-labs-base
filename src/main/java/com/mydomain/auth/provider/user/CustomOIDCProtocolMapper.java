package com.mydomain.auth.provider.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.ProtocolMapperUtils;
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

    ProviderConfigProperty property;

    property = new ProviderConfigProperty();
    property.setName(ProtocolMapperUtils.MULTIVALUED);
    property.setLabel(ProtocolMapperUtils.MULTIVALUED_LABEL);
    property.setHelpText(ProtocolMapperUtils.MULTIVALUED_HELP_TEXT);
    property.setType(ProviderConfigProperty.BOOLEAN_TYPE);
    configProperties.add(property);
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

    return "MyDomain Protocol Mapper";
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
    if (!OIDCAttributeMapperHelper.includeInIDToken(mappingModel)) {
//      IDToken tok = setIdStuff(token)
      return token;
    } else {
      IDToken tok = setIdTokenNameClaims(token, userSession);
      setClaim(tok, mappingModel, userSession, session, clientSessionCtx);
      return token;
    }
  }

  @Override
  public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel,
      KeycloakSession session, UserSessionModel userSession,
      ClientSessionContext clientSessionCtx) {
    log.info("SOAM: inside transformAccessToken");

    AccessToken augmentedToken = putIntoToken(token, userSession);

    setClaim(augmentedToken, mappingModel, userSession, session, clientSessionCtx);
    return augmentedToken;
  }

  private IDToken setIdTokenNameClaims(IDToken token, UserSessionModel session) {
    log.info("SOAM: inside setIdTokenNameClaims");

    // Just checking what we can get from the User
    UserModel model = session.getUser();
    log.info("User: {}", model.getUsername());

    token.setFamilyName(model.getLastName());
    token.setGivenName(model.getFirstName());
    token.setName(model.getFirstAttribute("name"));
    return token;
  }

  private AccessToken putIntoToken(AccessToken token, UserSessionModel session) {

    UserModel model = session.getUser();

    log.info("Attributes: {}", model.getAttributes()
        .values());

    // if (model.getFirstAttribute("phone") != null && !model.getFirstAttribute("phone").isBlank())
    //   token.getOtherClaims().put("https://mydomain.com/phone_number", model.getFirstAttribute("phone"));

    token.getOtherClaims().put("https://mydomain.com/user_roles",
        model.getRoleMappingsStream()
            .map(RoleModel::getName)
            .collect(Collectors.toUnmodifiableList()));
    token.getOtherClaims().put("https://mydomain.com/zoneinfo", "UTC");
//    token.getOtherClaims().put("https://mydomain.com/organization_id", System.getenv("ORGANIZATION_ID"));

    token.setSubject(model.getFirstAttribute("originalId"));
    token.addAudience(System.getenv("AUDIENCE"));
    token.addAudience("https://site.mydomain.com/api/v2/");

    return token;
  }

  public static ProtocolMapperModel create(String name, boolean accessToken, boolean idToken,
      boolean userInfo, String tokenClaimName) {

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
