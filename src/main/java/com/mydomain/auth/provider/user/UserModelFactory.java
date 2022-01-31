package com.mydomain.auth.provider.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.auth.provider.dto.CircleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;

public class UserModelFactory {
  private static final Logger LOG = Logger.getLogger(UserModelFactory.class);

  private final KeycloakSession session;
  private final ComponentModel model;

  public UserModelFactory(KeycloakSession session, ComponentModel model) {
    this.session = session;
    this.model = model;
  }

  public UserModel create(RealmModel realm, UserDetails record) {
    LOG.infof("Creating user model for: %s", record.getEmail());

    UserModel userModel;
    if (isEmpty(record.getId())) {
      userModel = session.userLocalStorage().addUser(realm, record.getEmail());
    } else {
      userModel = session.userLocalStorage().addUser(
          realm,
          record.getEmail(),
          record.getEmail(),
          true,
          false
      );
    }

    validateUsernamesEqual(record, userModel);

    userModel.setFederationLink(model.getId());
    userModel.setEnabled(true);
    userModel.setEmail(record.getEmail());
    userModel.setEmailVerified(true);
    userModel.setFirstName(record.getFirstName());
    userModel.setLastName(record.getLastName());

    userModel.setSingleAttribute("originalId", record.getId());
    userModel.setSingleAttribute("name", record.getFullName());
    userModel.setSingleAttribute("phone", record.getPhoneNumber());
    userModel.setSingleAttribute("parentToken", record.getSessionId());
    userModel.setSingleAttribute("organizationId", System.getenv("ORGANIZATION_ID"));

    if (record.getAdditionalInfo() != null) {
      String additionalInfo = record.getAdditionalInfo();

      getRoleModels(additionalInfo, realm)
        .forEach(userModel::grantRole);
    }
//
//    getRoleModels(legacyUser, realm)
//        .forEach(userModel::grantRole);
//
//    getGroupModels(legacyUser, realm)
//        .forEach(userModel::joinGroup);
//
//    if (legacyUser.getRequiredActions() != null) {
//      legacyUser.getRequiredActions()
//          .forEach(userModel::addRequiredAction);
//    }

    return userModel;
  }

  private Stream<RoleModel> getRoleModels(String additionalInfo, RealmModel realm) {

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
      LOG.error("[ERROR] roleMapping", e);
      e.printStackTrace();
    }

    return roles.stream()
        .map(r -> getRoleModel(realm, r))
        .filter(Optional::isPresent)
        .map(Optional::get);
  }

  private Optional<RoleModel> getRoleModel(RealmModel realm, String role) {
    return Optional.ofNullable(realm.getRole(role))
//        .or(() -> realm.getClientsStream()
//            .map(clientModel -> clientModel.getRole(finalRoleName))
//            .filter(Objects::nonNull)
//            .findFirst())
        .or(() -> {
          LOG.debug(String.format("Added role %s to realm %s", role, realm.getName()));
          return Optional.ofNullable(realm.addRole(role));
        });
  }

  private void validateUsernamesEqual(UserDetails record, UserModel userModel) {
    if (!userModel.getUsername().equals(record.getEmail())) {
      throw new IllegalStateException(String.format("Local and remote users differ: [%s != %s]",
          userModel.getUsername(),
          record.getEmail()));
    }
  }

  private boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
