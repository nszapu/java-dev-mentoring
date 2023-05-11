package com.epam.crud.service;

import com.epam.crud.config.Config;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.security.TokenGenerator;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@Import({AuthenticationService.class, Config.class, UserManagerService.class})
public class AuthenticationServiceIntegrationTest {

    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserDetailsManager userDetailsManager;
    @MockBean
    private TokenGenerator tokenGenerator;
    @MockBean
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Test
    public void register() {
//        given
        RegisterRequest request = new RegisterRequest();
        request.setName("lele");
        request.setEmail("lele@email.com");
        request.setPassword("password");
        Token expectedToken = new Token();
        expectedToken.setUserId(1);
        expectedToken.setAccessToken("test1");
        expectedToken.setRefreshToken("test2");
//        when
        when(tokenGenerator.createToken(any(Authentication.class))).thenReturn(expectedToken);
        Token token = service.register(request);
//        then
        assertEquals(expectedToken, token);
    }

    @Test
    public void registerEmailExists() {
//        given
        RegisterRequest request = new RegisterRequest();
        request.setName("lele");
        request.setEmail("lala@email.com");
        request.setPassword("password");
//        when
        Exception exception = assertThrows(EntityExistsException.class, () -> service.register(request));
//        then
        assertEquals("email is used", exception.getMessage());
    }
}
