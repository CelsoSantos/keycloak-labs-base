package com.mydomain.auth.provider.user;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Builder
public class ReadOnlyUserAdapter extends AbstractUserAdapter.Streams {

//  private final JwtAdditionalContentDto user;
  private static final Logger log = LoggerFactory.getLogger(ReadOnlyUserAdapter.class);

  @Getter
  private final String id;

  @Getter
  private final String username;

  @Getter
  private final String email;

  @Getter
  private final String firstName;

  @Getter
  private final String lastName;

  @Getter
  @Setter
  private String sessionId;

  @Getter
  @Setter
  private String phoneNumber;

  @Getter
  @Setter
  private String fullName;

  // TODO: this is not very nice since roles should be managed by getRoleMappings()
  //       but we need to go through the AbstractUserAdapter and use the inherited properties
  @Getter
  private final Set<String> roles;

  MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();

  public ReadOnlyUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserDetails user) {
    super(session, realm, model);
    log.info("[I41] create new ReadOnlyUserAdapter({})", user.getUsername());
    this.id = StorageId.keycloakId(model, user.getUsername());
//    this.storageId = new StorageId(storageProviderModel.getId(), user.getUserInfo().getUserEmail());

    this.username = user.getUsername();
    this.sessionId = user.getSessionId();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.roles = user.getRoles();
    this.phoneNumber = user.getPhoneNumber();
    this.fullName = user.getFullName();

//    attributes.add(UserModel.USERNAME, getUsername());
//    attributes.add(UserModel.EMAIL, getEmail());
//    attributes.add(UserModel.FIRST_NAME, getFirstName());
//    attributes.add(UserModel.LAST_NAME, getLastName());

    this.setSingleAttribute(UserModel.USERNAME, getUsername());
    this.setSingleAttribute(UserModel.EMAIL, getEmail());
    this.setSingleAttribute(UserModel.FIRST_NAME, getFirstName());
    this.setSingleAttribute(UserModel.LAST_NAME, getLastName());

    this.setSingleAttribute("parentToken", user.getSessionId());
    this.setSingleAttribute("name", user.getFullName());
    this.setSingleAttribute("userId", user.getId());
    this.setSingleAttribute("phoneNumber", user.getPhoneNumber());
    this.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));

  }

  public ReadOnlyUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
    super(session, realm, model);
    log.info("[I41] create new ReadOnlyUserAdapter({})", username);
    this.id = StorageId.keycloakId(model, username);
//    this.storageId = new StorageId(storageProviderModel.getId(), user.getUserInfo().getUserEmail());

    this.username = username;
    this.sessionId = "";
    this.email = username;
    this.firstName = "";
    this.lastName = "";
    this.roles = Collections.emptySet();
    this.phoneNumber = "";
    this.fullName = "";

//    attributes.add(UserModel.USERNAME, getUsername());
//    attributes.add(UserModel.EMAIL, getEmail());
//    attributes.add(UserModel.FIRST_NAME, getFirstName());
//    attributes.add(UserModel.LAST_NAME, getLastName());

    this.setSingleAttribute(UserModel.USERNAME, getUsername());
    this.setSingleAttribute(UserModel.EMAIL, getEmail());
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

  @Override
  public void setSingleAttribute(String name, String value) {
    attributes.add(name, value);
//    this.setSingleAttribute(name, value);
  }

  @Override
  public String getFirstAttribute(String name) {
    List<String> list = getAttributes().getOrDefault(name, List.of());
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public Map<String, List<String>> getAttributes() {
//    MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
//    attributes.add(UserModel.USERNAME, getUsername());
//    attributes.add(UserModel.EMAIL, getEmail());
//    attributes.add(UserModel.FIRST_NAME, getFirstName());
//    attributes.add(UserModel.LAST_NAME, getLastName());
//    attributes.add("parentToken", getSessionId());
//    attributes.add("name", getFullName());
//    attributes.add("organizationId", System.getenv("ORGANIZATION_ID"));
//    attributes.add("phoneNumber", getPhoneNumber());
//    attributes.add("zoneInfo", "UTC");
    return attributes;
  }
//
//  @Override
//  public Stream<String> getAttributeStream(String name) {
//    Map<String, List<String>> attributes = getAttributes();
//    return (attributes.containsKey(name)) ? attributes.get(name).stream() : Stream.empty();
//  }
//
//  @Override
//  protected Set<GroupModel> getGroupsInternal() {
//    if (user.getUserInfo().getGroups() != null) {
//      return user.getUserInfo().getGroups().stream().map(UserGroupModel::new).collect(Collectors.toSet());
//    }
//    return Set.of();
//  }
//
//  @Override
//  protected Set<RoleModel> getRoleMappingsInternal() {
//    if (user.getUserInfo().getUserRoles() != null) {
//      return user.getUserInfo().getUserRoles().stream().map(roleName -> new UserRoleModel(roleName, realm)).collect(Collectors.toSet());
//    }
//    return Set.of();
//  }

}
