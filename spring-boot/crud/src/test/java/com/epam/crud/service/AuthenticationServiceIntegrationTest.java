package com.epam.crud.service;

import com.epam.crud.config.Config;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.security.JwtToUserConverter;
import com.epam.crud.security.KeyUtils;
import com.epam.crud.security.TokenGenerator;
import com.epam.crud.security.WebSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@Import({AuthenticationService.class,
        UserManagerService.class,
        TokenGenerator.class,
        WebSecurity.class,
        JwtToUserConverter.class,
        KeyUtils.class,
        Config.class})
public class AuthenticationServiceIntegrationTest {

    @Autowired
    private AuthenticationService service;

    @Test
    public void register() {
        RegisterRequest request = new RegisterRequest();
        request.setName("lele");
        request.setEmail("lele@emial.com");
        request.setPassword("password");

        Token token = service.register(request);

        assertEquals(0, token.getAccessToken().length());
        assertEquals(0, token.getRefreshToken().length());
    }
}
