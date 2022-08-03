package com.popov.security_challenge.configuration.security_properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Binds the Oauth client info from application.yaml
 */
@Data
@Component
@ConfigurationProperties("security")
public class SecurityProperties {

    private JwtProperties jwt;

    @Data
    public static class JwtProperties {

        private Resource keyStore;

        private String keyStorePassword;

        private String keyPairAlias;

        private String keyPairPassword;

        private String clientId;

        private String secret;

        private Set<String> scopes;

        private Set<String> authorizedGrantTypes;

        private Collection<String> authorities;

        private Integer accessTokenValiditySeconds;

        private Integer refreshTokenValiditySeconds;
    }
}
