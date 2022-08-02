package com.popov.security_challenge.configuration;

import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * As per client requirements, refresh tokens have to be revocable!
 */
public interface CustomRefreshTokenStore extends TokenStore {
    void removeRefreshToken(String token);

}
