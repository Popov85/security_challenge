package com.popov.security_challenge.configuration.security_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security")
public class SecurityProperties {

    private JwtProperties jwt;

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public static class JwtProperties {

        private Resource keyStore;

        private String keyStorePassword;

        private String keyPairAlias;

        private String keyPairPassword;

        private String clientId;

        private String secret;

        public Resource getKeyStore() {
            return keyStore;
        }

        public void setKeyStore(Resource keyStore) {
            this.keyStore = keyStore;
        }

        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public String getKeyPairAlias() {
            return keyPairAlias;
        }

        public void setKeyPairAlias(String keyPairAlias) {
            this.keyPairAlias = keyPairAlias;
        }

        public String getKeyPairPassword() {
            return keyPairPassword;
        }

        public void setKeyPairPassword(String keyPairPassword) {
            this.keyPairPassword = keyPairPassword;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        @Override
        public String toString() {
            return "JwtProperties{" +
                    "keyStore=" + keyStore +
                    ", keyStorePassword='" + keyStorePassword + '\'' +
                    ", keyPairAlias='" + keyPairAlias + '\'' +
                    ", keyPairPassword='" + keyPairPassword + '\'' +
                    ", clientId='" + clientId + '\'' +
                    ", secret='" + secret + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SecurityProperties{" +
                "jwt=" + jwt +
                '}';
    }
}