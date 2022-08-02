package com.popov.security_challenge.configuration;

import com.popov.security_challenge.configuration.security_properties.SecurityProperties;
import com.popov.security_challenge.configuration.refresh_token.RefreshTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
public class CustomTokenStoreConfiguration {

    private final SecurityProperties securityProperties;

    private final CustomJwtTokenDecoder customJwtTokenDecoder;

    private final RefreshTokenRepository refreshTokenRepository;


    public CustomTokenStoreConfiguration(SecurityProperties securityProperties,
                                         CustomJwtTokenDecoder customJwtTokenDecoder,
                                         RefreshTokenRepository refreshTokenRepository) {
        this.securityProperties = securityProperties;
        this.customJwtTokenDecoder = customJwtTokenDecoder;
        this.refreshTokenRepository=refreshTokenRepository;
    }

    @Bean
    public CustomJwtRefreshTokenStore tokenStore() {
        // Replace with Redis refreshTokenRepository impl. if needed!
        return new CustomJwtRefreshTokenStore(customJwtTokenDecoder, refreshTokenRepository, jwtAccessTokenConverter());
    }

    @Bean
    public CustomJwtAccessTokenConverter jwtAccessTokenConverter() {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
        CustomJwtAccessTokenConverter jwtAccessTokenConverter = new CustomJwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}
