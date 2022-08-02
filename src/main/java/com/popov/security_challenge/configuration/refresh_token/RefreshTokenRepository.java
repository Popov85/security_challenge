package com.popov.security_challenge.configuration.refresh_token;

import com.popov.security_challenge.configuration.CustomJwtTokenDecoder;

public interface RefreshTokenRepository {
    public String deleteById(String token);
    public void save(CustomJwtTokenDecoder.JwtPrincipal jwtPrincipal, String tokenValue);
}
