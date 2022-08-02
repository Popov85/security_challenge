package com.popov.security_challenge.configuration;

public interface RefreshTokenRepository {
    public String deleteById(String token);

    public void save(CustomJwtTokenDecoder.JwtPrincipal jwtPrincipal, String tokenValue);
}
