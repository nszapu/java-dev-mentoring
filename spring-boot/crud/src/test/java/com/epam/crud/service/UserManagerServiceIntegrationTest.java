package com.epam.crud.service;

import com.epam.crud.config.Config;
import com.epam.crud.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserManagerService.class, Config.class})
public class UserManagerServiceIntegrationTest {

    @Autowired
    private UserManagerService service;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User(3, "lele", "lele@email.com", "password");
    }

    @Test
    public void createUser() {
        service.createUser(user);
        assertTrue(service.userExists("lele@email.com"));
    }

    @Test
    public void userExists() {
        assertTrue(service.userExists("lala@email.com"));
    }

    @Test
    public void loadUserByUsername() {
        UserDetails actual = service.loadUserByUsername("lala@email.com");
        assertEquals("lala@email.com", actual.getUsername());
        assertEquals("password", actual.getPassword());
    }

    @Test
    public void loadUserByUsernameNotFound() {
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("not_valid_username"));
        assertEquals("email not_valid_username not found", exception.getMessage());
    }
}
