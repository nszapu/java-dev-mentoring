package com.epam.crud.service;

import com.epam.crud.config.Config;
import com.epam.crud.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserService.class, Config.class})
public class UserServiceIntegrationTest {

    @Autowired
    private UserService service;
    private User user1;
    private List<User> users;

    @BeforeEach
    public void setup() {
        user1 = new User(1, "lala", "lala@email.com", "password");
        User user2 = new User(2, "lolo", "lolo@email.com", "password");
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void getUser() {
        assertEquals(user1, service.getUser(1));
    }

    @Test
    public void getUserNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.getUser(99));
    }

    @Test
    public void getUsers() {
        assertEquals(users, service.getUsers());
    }

    @Test
    public void save() {
        User user = new User(3, "lele", "lele@email.com", "password");
        users.add(user);
        assertEquals(user, service.save(user));
        assertEquals(users, service.getUsers());
    }

    @Test
    public void modify() {
        User user = new User(1, "lele", "lele@email.com", "password");
        assertEquals(user, service.save(user));
        assertEquals(user, service.getUser(1));
    }

    @Test
    public void delete() {
        service.delete(1);
        assertThrows(NoSuchElementException.class, () -> service.getUser(1));
    }
}
