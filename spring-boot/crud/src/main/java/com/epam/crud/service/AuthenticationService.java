package com.epam.crud.service;

import com.epam.crud.model.AuthenticationRequest;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.model.User;
import com.epam.crud.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    public Token register(RegisterRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), Collections.emptyList());
        return tokenGenerator.createToken(authentication);
    }

    public Token authenticate(AuthenticationRequest request) {
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword()));
        return tokenGenerator.createToken(authentication);
    }
}
