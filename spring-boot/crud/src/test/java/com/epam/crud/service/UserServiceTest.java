package com.epam.crud.service;

import com.epam.crud.model.User;
import com.epam.crud.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
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
        when(repository.findById(1L)).thenReturn(Optional.of(user1));
        assertEquals(user1, service.getUser(1));
    }

    @Test
    public void getUsers() {
        when(repository.findAll()).thenReturn(users);
        assertEquals(users, service.getUsers());
    }

    @Test
    public void save() {
        when(repository.save(user1)).thenReturn(user1);
        assertEquals(user1, service.save(user1));
    }

    @Test
    public void delete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1);
    }
}
