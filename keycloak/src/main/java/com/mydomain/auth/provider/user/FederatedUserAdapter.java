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

  // TODO: this is not very nice since roles should be managed by getRoleMappings()
  //       but we need to go through the AbstractUserAdapter and use the inherited properties
  @Getter
  private final Set<String> roles;


  public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
    super(session, realm, model);

    log.info("[I101] create new FederatedUserAdapter with ID {}", StorageId.keycloakId(model, username));
    this.id = StorageId.keycloakId(model, username);

    this.username = username;
    this.email = username;
    this.firstName = "";
    this.lastName = "";
    this.roles = Collections.emptySet();

    this.setSingleAttribute(UserModel.USERNAME, username);
    this.setSingleAttribute(UserModel.EMAIL, username);
    // this.setSingleAttribute(UserModel.FIRST_NAME, getFirstName());
    // this.setSingleAttribute(UserModel.LAST_NAME, getLastName());

    this.setFederationLink(model.getId());
    this.setEmailVerified(true);
    this.setEnabled(true);

    this.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));

  }
}
