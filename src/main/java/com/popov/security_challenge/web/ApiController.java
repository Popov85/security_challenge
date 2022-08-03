package com.popov.security_challenge.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@PreAuthorize("hasRole('ROLE_API')")
public class ApiController {

    @GetMapping(value = "/echo/{name}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> echo(@PathVariable String name) {
        log.debug("Requested echo name= {}", name);
        return ResponseEntity.ok(name);
    }

    @GetMapping(value = "/ok", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ok() {
        log.debug("Requested ok page");
        return ResponseEntity.ok("OK");
    }
}