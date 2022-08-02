package com.popov.security_challenge.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Component
public class CustomJwtTokenDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJwtTokenDecoder.class);

    private final SecurityProperties securityProperties;

    public CustomJwtTokenDecoder(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public static class JwtPrincipal {
        private final Long userId;
        private final String username;
        private final LocalDateTime expirationDate;

        public JwtPrincipal(Long userId, String username, LocalDateTime expirationDate) {
            this.userId = userId;
            this.username = username;
            this.expirationDate = expirationDate;
        }

        public Long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public LocalDateTime getExpirationDate() {
            return expirationDate;
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

    public JwtPrincipal parseJwtToken(String token) {
        try {
            SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
            KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(keyPair.getPublic())
                    .parseClaimsJws(token);
            Claims claims = parsedToken.getBody();

            Long userId = null;
            if (Objects.nonNull(claims.get("user_id"))) {
                userId = Long.valueOf((Integer) claims.get("user_id"));
            }

            String username = null;
            if (Objects.nonNull(claims.get("user_name"))) {
                username = (String) claims.get("user_name");
            }

            LocalDateTime expirationDate = null;
            if (Objects.nonNull(claims.get("exp"))) {
                expirationDate =
                        LocalDateTime.ofInstant(Instant.ofEpochSecond((Integer) claims.get("exp")), ZoneId.systemDefault());
            }

            LOGGER.debug("CustomJwtTokenDecoder, JwtPrincipal = {}", new JwtPrincipal(userId, username, expirationDate));

            return new JwtPrincipal(userId, username, expirationDate);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse JWT token!", ex);
        }
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}
