package com.popov.security_challenge.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserPrincipal getUserPrincipal() {
        return (UserPrincipal) getAuthentication().getPrincipal();
    }

    @Override
    public boolean isAdmin() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        return authorities.contains("ROLE_ADMIN");
    }

    @Override
    public boolean isSuperAdmin() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        return authorities.contains("ROLE_ADMIN");
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
