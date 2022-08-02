package com.popov.security_challenge.configuration.security_principals;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JwtPrincipal {
    private final Long userId;
    private final String username;
    private final LocalDateTime expirationDate;

    public JwtPrincipal(Long userId, String username, LocalDateTime expirationDate) {
        this.userId = userId;
        this.username = username;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "JwtPrincipal{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
