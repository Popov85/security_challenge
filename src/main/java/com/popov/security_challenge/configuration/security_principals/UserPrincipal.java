package com.popov.security_challenge.configuration.security_principals;

import com.popov.security_challenge.repository.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

@Data
public class UserPrincipal implements Principal {

    private final String username;

    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    // Additional fields required to be stored inside JWT token

    private final Long userId;

    private final Long companyId;

    private final String company;

    private final Set<Role> roles;

    public UserPrincipal(Long userId, String username, Long companyId, String company, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.companyId = companyId;
        this.company = company;
        this.roles = roles;
        this.grantedAuthorities = roles;
    }

    @Override
    public String getName() {
        return this.username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompany() {
        return company;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserPrincipal {" +
                "userId='" + userId + '\'' +
                "username='" + username + '\'' +
                "companyId='" + companyId + '\'' +
                "company='" + company + '\'' +
                ", roles=" + roles +
                '}';
    }
}
