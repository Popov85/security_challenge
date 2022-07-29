package com.popov.security_challenge.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final CustomClientDetailsService clientDetailsService;

    private final CustomRefreshTokenStore customRefreshTokenStore;

    private final CustomJwtAccessTokenConverter customJwtAccessTokenConverter;

    private final CustomPreBasicAuthenticationFilter customPreBasicAuthenticationFilter;

    public AuthorizationServerConfiguration(PasswordEncoder passwordEncoder,
                                            AuthenticationManager authenticationManager,
                                            CustomUserDetailsService userDetailsService,
                                            CustomClientDetailsService clientDetailsService,
                                            CustomRefreshTokenStore customRefreshTokenStore,
                                            CustomJwtAccessTokenConverter customJwtAccessTokenConverter,
                                            CustomPreBasicAuthenticationFilter customPreBasicAuthenticationFilter) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.customRefreshTokenStore = customRefreshTokenStore;
        this.customJwtAccessTokenConverter = customJwtAccessTokenConverter;
        this.customPreBasicAuthenticationFilter = customPreBasicAuthenticationFilter;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientId")
                .authorizedGrantTypes("password","refresh_token","client_credentials")
                .authorities("ROLE_CLIENT")
                .secret("$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.")
                .scopes("read","write")
                .accessTokenValiditySeconds(1000)
                .refreshTokenValiditySeconds(10000)
                .and()
                .withClient("clientId2"); // TODO to be continued
//        clients
//                .withClientDetails(this.clientDetailsService);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(this.customJwtAccessTokenConverter)
                .userDetailsService(this.userDetailsService)
                .tokenStore(this.customRefreshTokenStore)
                .exceptionTranslator(new CustomExceptionTranslator());
        Map<String, CorsConfiguration> corsConfigMap = new LinkedHashMap<>();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        corsConfigMap.put("/oauth/token", config);
        endpoints.getFrameworkEndpointHandlerMapping()
                .setCorsConfigurations(corsConfigMap);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .passwordEncoder(this.passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients() // adds a possibility to pass credentials via form params;
                .addTokenEndpointAuthenticationFilter(this.customPreBasicAuthenticationFilter); // adds Basic auth header with server secret
    }
}
