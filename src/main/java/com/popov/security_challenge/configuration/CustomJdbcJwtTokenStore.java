package com.popov.security_challenge.configuration;


import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Jdbc implementation of refresh tokens store (client requirement)
 * This is rather slow (up to 1000 users) but persistent impl.
 */
public class CustomJdbcJwtTokenStore extends JwtTokenStore implements CustomRefreshTokenStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJdbcJwtTokenStore.class);

    private static final String KEY = "GA9LY1VX";

    private final AuthService authService;

    private final CustomJwtTokenDecoder customJwtTokenDecoder;

    private final RefreshTokenRepository refreshTokenRepository;

    public CustomJdbcJwtTokenStore(AuthService authService,
                                   CustomJwtTokenDecoder customJwtTokenDecoder,
                                   RefreshTokenRepository refreshTokenRepository,
                                   JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
        this.authService = authService;
        this.customJwtTokenDecoder = customJwtTokenDecoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        // /refresh_token endpoint (more often)
        LOGGER.debug("Read refresh token = {}", tokenValue);
        String encodedToken = toMD5Hash(tokenValue);
        if (!refreshTokenRepository.existsById(encodedToken)) {
            throw new SecurityException("Access denied!");
        }
        // It will not be accepted any more
        refreshTokenRepository.deleteById(encodedToken);
        return super.readRefreshToken(tokenValue);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // It is fired after new pair is generated on 1) login, 2) refresh!
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            String tokenValue = refreshToken.getValue();
            CustomJwtTokenDecoder.JwtPrincipal jwtPrincipal
                    = customJwtTokenDecoder.parseJwtToken(tokenValue);
            LOGGER.debug("Store refresh token of user = {} token = {}", jwtPrincipal, token);
            Long userId = jwtPrincipal.getUserId();
            LocalDateTime expirationDate = jwtPrincipal.getExpirationDate();
            RefreshToken newRefreshToken =
                    new RefreshToken(toMD5Hash(tokenValue),
                            new User("user", "password",
                                    Arrays.asList((GrantedAuthority) () -> "ADMIN")), expirationDate);
            refreshTokenRepository.save(newRefreshToken);
        }
    }

    @Override
    public void removeRefreshToken(String token) {
        UserPrincipal currentPrincipal = authService.getUserPrincipal();
        Long userId = currentPrincipal.getUserId();
        CustomJwtTokenDecoder.JwtPrincipal jwtPrincipal
                = customJwtTokenDecoder.parseJwtToken(token);
        LOGGER.debug("Remove refresh token for user = {}, token = {}", jwtPrincipal, token);
        if (!Objects.equals(jwtPrincipal.getUserId(), userId)) {
            throw new SecurityException("Access denied!");
        }
        String encodedToken = toMD5Hash(token);
        if (!refreshTokenRepository.existsById(encodedToken)) {
            throw new SecurityException("Access denied!");
        }
        refreshTokenRepository.deleteById(encodedToken);
    }

    private String toMD5Hash(String refreshToken) {
        return DigestUtils.md5Hex(KEY+refreshToken);
    }

}
