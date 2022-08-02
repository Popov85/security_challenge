package com.popov.security_challenge.configuration.service;


import com.popov.security_challenge.configuration.UserPrincipal;

public interface AuthService {

    UserPrincipal getUserPrincipal();

    boolean isAdmin();

    boolean isApiUser();

}
