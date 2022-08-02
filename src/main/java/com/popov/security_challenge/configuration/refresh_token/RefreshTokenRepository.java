package com.popov.security_challenge.configuration.refresh_token;

public interface RefreshTokenRepository {
    public String deleteById(String refreshToken);
    public void save(Long userId, String refreshToken);
}
