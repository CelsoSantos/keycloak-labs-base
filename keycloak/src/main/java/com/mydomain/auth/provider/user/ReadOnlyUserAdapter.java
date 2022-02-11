package com.mydomain.auth.provider.user;

import lombok.Getter;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Builder
public class ReadOnlyUserAdapter extends AbstractUserAdapter.Streams {

  private static final Logger log = LoggerFactory.getLogger(ReadOnlyUserAdapter.class);

  @Getter
  private final String id;

  @Getter
  private final String username;

  @Getter
  private final String email;

  public ReadOnlyUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
    super(session, realm, model);
    log.info("[I41] create new ReadOnlyUserAdapter({})", username);
    this.id = StorageId.keycloakId(model, username);

    this.username = username;
    this.email = username;
  }
}
