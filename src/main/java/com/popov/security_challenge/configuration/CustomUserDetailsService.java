package com.popov.security_challenge.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("CustomClientDetailsService in action ... "+username);
        LoginUserPrincipal userPrincipal =
                new LoginUserPrincipal(1L, "user", 1L, "IBM", UserRole.ROLE_ADMIN);
        LOGGER.debug("loadUserByUsername, UserPrincipal = {}", userPrincipal);
        return userPrincipal;
    }
}
