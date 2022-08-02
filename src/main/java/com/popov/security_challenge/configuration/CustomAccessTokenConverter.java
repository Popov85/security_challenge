package com.popov.security_challenge.configuration;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * Used to remove not necessary fields from JWT payload
 */
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, ?> stringMap = super.convertAccessToken(token, authentication);
        //stringMap.remove("client_id"); // Cannot be removed! For refresh token!
        stringMap.remove(OAuth2AccessToken.SCOPE);
        return stringMap;
    }
}
