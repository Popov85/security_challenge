package com.popov.security_challenge.configuration;

public interface RefreshTokenRepository {

    public boolean existsById(String token);

    public void deleteById(String token);

    public void save(RefreshToken token);
}
