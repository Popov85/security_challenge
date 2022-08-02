package com.popov.security_challenge.configuration.auth_service;


import com.popov.security_challenge.configuration.security_principals.UserPrincipal;

public interface AuthService {

    UserPrincipal getUserPrincipal();

    boolean isAdmin();

    boolean isApiUser();

}
