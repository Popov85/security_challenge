package com.popov.security_challenge.configuration;

import com.popov.security_challenge.configuration.security_principals.LoginUserPrincipal;
import com.popov.security_challenge.repository.CredentialsRepository;
import com.popov.security_challenge.repository.entity.AppUser;
import com.popov.security_challenge.repository.entity.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    public CustomUserDetailsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credentials> byUsername =
                credentialsRepository.findByUsername(username);
        Credentials credentials =
                byUsername.orElseThrow(() -> new SecurityException("User not found!"));
        AppUser user = credentials.getUser();
        LoginUserPrincipal userPrincipal =
                new LoginUserPrincipal(credentials.getUserId(),
                        credentials.getUsername(), credentials.getPassword(),
                        user.getCompany().getId(), user.getCompany().getName(), user.getRoles());
        log.debug("LoginUserPrincipal = {}", userPrincipal);
        return userPrincipal;
    }
}
