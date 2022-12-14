package com.popov.security_challenge.configuration;

import com.popov.security_challenge.configuration.security_principals.LoginUserPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Used to add more params to JWT token
 */
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new LinkedHashMap<>();
        LoginUserPrincipal user
                = (LoginUserPrincipal) authentication.getPrincipal();
        additionalInfo.put("user_id", user.getUserId());
        additionalInfo.put("authorities", user.getRoles()
                .stream().map(r->r.getName()).collect(Collectors.toSet()));
        additionalInfo.put("company", user.getCompany());
        additionalInfo.put("company_id", user.getCompanyId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return super.enhance(accessToken, authentication);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }

    @PostConstruct
    public void post() {
        this.setAccessTokenConverter(new CustomAccessTokenConverter());
    }
}
