package com.mydomain.auth.provider.user;

import java.util.Set;

public interface UserDetails {

//    String getOrganizationId();

    String getId();

    String getUsername();

    String getEmail();

    Set<String> getRoles();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getFullName();

    String getSessionId();

    String getAdditionalInfo();

}
