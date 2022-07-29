package com.popov.security_challenge.configuration;


public interface AuthService {

    UserPrincipal getUserPrincipal();

    boolean isAdmin();

    boolean isSuperAdmin();

}
