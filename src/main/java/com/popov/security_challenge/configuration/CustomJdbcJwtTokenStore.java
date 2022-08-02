package com.popov.security_challenge.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Objects;

/**
 * Jdbc implementation of refresh tokens store (client requirement)
 * This is rather slow (up to 1000 users) but persistent impl.
 */
@Slf4j
public class CustomJdbcJwtTokenStore extends JwtTokenStore implements CustomRefreshTokenStore {

    private final CustomJwtTokenDecoder customJwtTokenDecoder;

    private final RefreshTokenRepository refreshTokenRepository;

    public CustomJdbcJwtTokenStore(CustomJwtTokenDecoder customJwtTokenDecoder,
                                   RefreshTokenRepository refreshTokenRepository,
                                   JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
        this.customJwtTokenDecoder = customJwtTokenDecoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        // It will not be accepted anymore
        String refreshToken = refreshTokenRepository.deleteById(tokenValue);
        if (refreshToken==null) {
            throw new SecurityException("Access denied! Refresh token does not exist!");
        }
        return super.readRefreshToken(tokenValue);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // It is fired after new pair is generated on 1) login, 2) refresh!
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            String refreshTokenValue = refreshToken.getValue();
            CustomJwtTokenDecoder.JwtPrincipal jwtPrincipal
                    = customJwtTokenDecoder.parseJwtToken(refreshTokenValue);
            refreshTokenRepository.save(jwtPrincipal, refreshTokenValue);
        }
    }

    @Override
    public void removeRefreshToken(String tokenValue) {
        refreshTokenRepository.deleteById(tokenValue);
    }
}
