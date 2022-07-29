package com.popov.security_challenge.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * Used to adjust error message for oauth2 flow exceptions
 */
public class CustomExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        return new ResponseEntity<>(
                new CustomExceptionHandler.Error(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
