package com.popov.security_challenge.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserPrincipal extends UserPrincipal implements UserDetails {

    @JsonIgnore
    private String password;

    public LoginUserPrincipal(Long userId, String username, Long companyId, String company, UserRole role) {
        super(userId, username, companyId, company, role);
        this.password = "$2a$10$E.IxZc5omB91t.P6E1uSO.TKspOE5QcCWEZMfZEgCAb7.YZIUN4P2";
    }


    @Override
    public String getPassword() {
        return this.password;
    }


}
