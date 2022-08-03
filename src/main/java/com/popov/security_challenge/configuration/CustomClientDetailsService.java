package com.popov.security_challenge.configuration;


import com.popov.security_challenge.configuration.security_properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
/**
 * Clients are usually stored in a DB, if we have the only client, hardcoded is probably ok!
 * @see <a href="https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/provider/ClientDetails.html#isSecretRequired()">Doc</a>
 */
public class CustomClientDetailsService implements ClientDetailsService {

    private final SecurityProperties securityProperties;

    private final PasswordEncoder encoder;

    public CustomClientDetailsService(SecurityProperties securityProperties, PasswordEncoder encoder) {
        this.securityProperties = securityProperties;
        this.encoder = encoder;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //log.debug("Loading a client, clientId = {}", clientId);
        SecurityProperties.JwtProperties jwt = securityProperties.getJwt();
           return new ClientDetails() {
               @Override
               public String getClientId() {
                   return jwt.getClientId();
               }

               @Override
               public Set<String> getResourceIds() {
                   return Collections.emptySet();
               }

               @Override
               public boolean isSecretRequired() {
                   return false;
               }

               @Override
               public String getClientSecret() {
                   return encoder.encode(jwt.getSecret());
               }

               @Override
               public boolean isScoped() {
                   return false;
               }

               @Override
               public Set<String> getScope() {
                   return jwt.getScopes();
               }

               @Override
               public Set<String> getAuthorizedGrantTypes() {
                   return jwt.getAuthorizedGrantTypes();
               }

               @Override
               public Set<String> getRegisteredRedirectUri() {
                   return Collections.emptySet();
               }

               @Override
               public Collection<GrantedAuthority> getAuthorities() {
                   return jwt.getAuthorities().stream()
                           .map(a-> (GrantedAuthority) () -> a).collect(Collectors.toList());
               }

               @Override
               public Integer getAccessTokenValiditySeconds() {
                   return jwt.getAccessTokenValiditySeconds();
               }

               @Override
               public Integer getRefreshTokenValiditySeconds() {
                   return jwt.getRefreshTokenValiditySeconds();
               }

               @Override
               public boolean isAutoApprove(String s) {
                   return false;
               }

               @Override
               public Map<String, Object> getAdditionalInformation() {
                   return Collections.emptyMap();
               }
           };
    }
}
