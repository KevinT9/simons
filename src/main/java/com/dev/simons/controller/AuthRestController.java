package com.dev.simons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    public AuthRestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
//        return ResponseEntity.ok().build();
//    }

    public record LoginRequest(String username, String password) {
    }


}