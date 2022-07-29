package com.popov.security_challenge.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PublicController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OAuth2AuthenticationToken> token(OAuth2AuthenticationToken token) {
        log.debug("Token = {}", token);
        return ResponseEntity.ok(token);
    }

    @GetMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> session(HttpSession session) {
        log.debug("Session = {}", session);
        Enumeration<String> attributeNames = session.getAttributeNames();

        Map<String, Object> result = Collections.list(attributeNames).stream()
                .collect(Collectors.toMap(attributeName -> attributeName, session::getValue));

        return ResponseEntity.ok(result);
    }
}
