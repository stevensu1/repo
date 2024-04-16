package com.mybatisflex.test;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class MyUserStorageProviderFactory implements UserStorageProviderFactory<MyUserStorageProvider> {

    private static final Logger logger = Logger.getLogger(MyUserStorageProviderFactory.class);

    @Override
    public MyUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {

        logger.info("create()");
        return new MyUserStorageProvider(keycloakSession, componentModel);
    }

    @Override
    public String getId() {

        return "mybatis-flex-native";
    }

    @Override
    public String getHelpText() {

        return "mybatis-flex-native";
    }

    @Override
    public void close() {

        logger.info("<<<<<< Closing factory");

    }
}
