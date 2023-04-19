package com.epam.crud.controller;

import com.epam.crud.model.AuthenticationRequest;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.service.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody RegisterRequest request) {
        try {
            return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Token> authenticate(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }
}
