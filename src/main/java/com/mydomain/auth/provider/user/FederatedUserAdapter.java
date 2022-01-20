package com.mydomain.auth.provider.user;

import java.util.Collections;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Builder
public class FederatedUserAdapter extends AbstractUserAdapterFederatedStorage {

//  private final JwtAdditionalContentDto user;
  private static final Logger log = LoggerFactory.getLogger(FederatedUserAdapter.class);

  @Getter
  private final String id;

  @Getter
  @Setter
  private String username;

  @Getter
  private final String email;

  @Getter
  private final String firstName;

  @Getter
  private final String lastName;

//  @Getter
//  @Setter
//  private String sessionId;

//  @Getter
//  @Setter
//  private String phoneNumber;
//
//  @Getter
//  @Setter
//  private String fullName;

  // TODO: this is not very nice since roles should be managed by getRoleMappings()
  //       but we need to go through the AbstractUserAdapter and use the inherited properties
  @Getter
  private final Set<String> roles;

//  MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();

  public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserDetails user) {
    super(session, realm, model);
    log.info("[I41] create new FederatedUserAdapter({})", user.getUsername());
    this.id = StorageId.keycloakId(model, user.getUsername());
//    this.storageId = new StorageId(storageProviderModel.getId(), user.getUserInfo().getUserEmail());

    this.username = user.getEmail();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.roles = user.getRoles();
//    this.sessionId = user.getSessionId();
//    this.phoneNumber = user.getPhoneNumber();
//    this.fullName = user.getFullName();

    this.setSingleAttribute(UserModel.USERNAME, user.getEmail());
    this.setSingleAttribute(UserModel.EMAIL, user.getEmail());
    this.setSingleAttribute(UserModel.FIRST_NAME, user.getFirstName());
    this.setSingleAttribute(UserModel.LAST_NAME, user.getLastName());

    this.setSingleAttribute("parentToken", user.getSessionId());
    this.setSingleAttribute("name", user.getFullName());
    this.setSingleAttribute("userId", user.getUserId());
    this.setSingleAttribute("phoneNumber", user.getPhoneNumber());
    this.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));

  }

  public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
    super(session, realm, model);
    log.info("[I41] create new FederatedUserAdapter({})", username);
    this.id = StorageId.keycloakId(model, username);
//    this.storageId = new StorageId(storageProviderModel.getId(), user.getUserInfo().getUserEmail());

    this.username = username;
    this.email = username;
    this.firstName = "";
    this.lastName = "";
    this.roles = Collections.emptySet();
//    this.sessionId = "";
//    this.phoneNumber = "";
//    this.fullName = "";

    this.setSingleAttribute(UserModel.USERNAME, username);
    this.setSingleAttribute(UserModel.EMAIL, username);
    this.setSingleAttribute(UserModel.FIRST_NAME, getFirstName());
    this.setSingleAttribute(UserModel.LAST_NAME, getLastName());

//    this.setSingleAttribute("parentToken", sessionId);
//    this.setSingleAttribute("name", fullName);
//    this.setSingleAttribute("userId", userId);
//    this.setSingleAttribute("phoneNumber", phoneNumber);
//    this.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));

  }

  @Override
  public void grantRole(RoleModel role) {
    this.session.userFederatedStorage().grantRole(realm, this.getId(), role);
  }

}
