package com.mydomain.auth.provider.user;

import java.util.Collections;
import java.util.List;
import org.keycloak.Config.Scope;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.UserStorageProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CustomUserStorageProviderFactory
    implements UserStorageProviderFactory<CustomUserStorageProvider> {

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProviderFactory.class);
  public static final String PROVIDER_NAME = "thirdparty-user-provider";

  @Override
  public CustomUserStorageProvider create(KeycloakSession ksession, ComponentModel model) {
    log.info("[I00] creating new CustomUserStorageProvider");
    return new CustomUserStorageProvider(ksession, model);
  }

  @Override
  public String getId() {
    log.info("[I900] getId()");
    return PROVIDER_NAME;
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
  }

  @Override
  public void postInit(KeycloakSessionFactory factory) {
    // TODO Auto-generated method stub
  }

  @Override
  public void init(Scope config) {
    // TODO Auto-generated method stub
  }

  @Override
  public UserStorageProvider create(KeycloakSession session) {
    return null;
    // TODO Auto-generated method stub
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    // TODO Auto-generated method stub
    return Collections.emptyList();
  }

  @Override
  public String getHelpText() {
    // TODO Auto-generated method stub
    return "";
  }
}
