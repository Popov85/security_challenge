package com.popov.security_challenge.configuration.security_principals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.popov.security_challenge.repository.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class LoginUserPrincipal extends UserPrincipal implements UserDetails {

    @JsonIgnore
    private String password;

    public LoginUserPrincipal(Long userId, String username, String password, Long companyId, String company, Set<Role> roles) {
        super(userId, username, companyId, company, roles);
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
