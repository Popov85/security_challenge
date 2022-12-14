package com.popov.security_challenge.configuration.refresh_token;

import com.popov.security_challenge.repository.CredentialsRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RefreshTokenRepositoryInMemoryImpl implements RefreshTokenRepository {
    private static final String KEY = "TN3LP28WSD";

    // K - MD5 hashed refresh token value, V - bare refresh token value
    private final Map<String, String> inMemoryRepository = new ConcurrentHashMap<>();

    private final CredentialsRepository credentialsRepository;

    public RefreshTokenRepositoryInMemoryImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public String deleteById(String refreshToken) {
        String encodedToken = toMD5Hash(refreshToken);
        String removed =
                inMemoryRepository.remove(encodedToken);
        return removed;
    }

    @Override
    public void save(Long userId, String refreshToken) {
        if (!credentialsRepository.existsById(userId))
                throw new SecurityException("User not found!");
        inMemoryRepository.put(toMD5Hash(refreshToken), refreshToken);
        return;
    }

    private String toMD5Hash(String refreshToken) {
        return DigestUtils.md5Hex(KEY+refreshToken);
    }
}
