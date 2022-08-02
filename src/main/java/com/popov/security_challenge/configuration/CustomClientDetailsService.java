package com.popov.security_challenge.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Primary
/**
 * Clients are usually stored in a DB, if we have the only client, hardcoded is ok!
 */
public class CustomClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
           return new ClientDetails() {
               @Override
               public String getClientId() {
                   return "clientId";
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
                   return "$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.";
               }

               @Override
               public boolean isScoped() {
                   return false;
               }

               @Override
               public Set<String> getScope() {
                   return new HashSet<>(Arrays.asList("read","write"));
               }

               @Override
               public Set<String> getAuthorizedGrantTypes() {
                   return new HashSet<>(Arrays.asList("password","refresh_token","client_credentials"));
               }

               @Override
               public Set<String> getRegisteredRedirectUri() {
                   return Collections.emptySet();
               }

               @Override
               public Collection<GrantedAuthority> getAuthorities() {
                   return Arrays.asList((GrantedAuthority) () -> "ROLE_CLIENT");
               }

               @Override
               public Integer getAccessTokenValiditySeconds() {
                   return 100;
               }

               @Override
               public Integer getRefreshTokenValiditySeconds() {
                   return 100;
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
