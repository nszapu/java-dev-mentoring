package com.epam.core.service;

import com.epam.core.dto.UserDto;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import com.epam.core.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService userService;
    private User user;
    private List<User> users;
    private UserEntity userEntity;
    private List<UserEntity> userEntities;

    @Before
    public void setup() {
        user = new UserDto();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@123.abc");
        users = new ArrayList<>();
        users.add(user);
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("test");
        userEntity.setEmail("test@123.abc");
        userEntities = new ArrayList<>();
        userEntities.add(userEntity);
    }

    @Test
    public void testLoadUsersFromFile() {

    }

    @Test
    public void testGetUserById() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(userEntity));
        User result = userService.getUserById(1);
        assertEquals(user, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetUserByIdFail() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        userService.getUserById(2);
    }

    @Test
    public void testGetUserByEmail() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.ofNullable(userEntity));
        User result = userService.getUserByEmail("test@123.abc");
        assertEquals(user, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetUserByEmailFail() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        userService.getUserByEmail("test1@123.abc");
    }

    @Test
    public void testGetUsersByName() {
        when(repository.findByName(anyString())).thenReturn(userEntities);
        List<User> result = userService.getUsersByName("test", 1, 1);
        assertEquals(users, result);
    }

    @Test
    public void testCreateUser() {
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);
        User result = userService.createUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);
        User result = userService.updateUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testDeleteUser() {
    }
}
