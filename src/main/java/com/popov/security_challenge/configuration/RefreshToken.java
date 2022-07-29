package com.popov.security_challenge.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class RefreshToken {

    private String refreshToken;

    private User user;

    private LocalDateTime expirationDate;

    public RefreshToken() {
    }

    public RefreshToken(String refreshToken, User user, LocalDateTime expirationDate) {
        this.refreshToken = refreshToken;
        this.user = user;
        this.expirationDate = expirationDate;
    }
}
