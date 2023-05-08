package com.epam.crud.service;

import com.epam.crud.model.AuthenticationRequest;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.model.User;
import com.epam.crud.repo.UserRepository;
import com.epam.crud.security.TokenGenerator;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private UserRepository repository;

    public Token register(RegisterRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
        if (repository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("email is used");
        }
        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), Collections.emptyList());
        log.info("User is registered with {} email.", user.getEmail());
        return tokenGenerator.createToken(authentication);
    }

    public Token authenticate(AuthenticationRequest request) {
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword()));
        log.info("User is authenticated with {} email.", request.getEmail());
        return tokenGenerator.createToken(authentication);
    }
}
