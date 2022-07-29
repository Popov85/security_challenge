package com.popov.security_challenge.configuration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ROLE_SUPER_ADMIN,

    ROLE_ADMIN,

    ROLE_API_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
