package com.popov.security_challenge.configuration;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RefreshTokenRepositoryInMemoryImpl implements RefreshTokenRepository {

    private final Map<String, RefreshToken> repo = new HashMap<>();

    @Override
    public boolean existsById(String token) {
        return repo.get(token)!=null;
    }

    @Override
    public void deleteById(String token) {
        repo.remove(token);
        return;
    }

    @Override
    public void save(RefreshToken token) {
        repo.put(token.getRefreshToken(), token);
        return;
    }
}
