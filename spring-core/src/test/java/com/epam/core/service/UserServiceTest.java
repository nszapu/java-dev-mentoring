package com.epam.core.service;

import com.epam.core.dao.UserDao;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private User user;
    private List<User> users;

    @Before
    public void setup() {
        user = new UserEntity();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@123.abc");
        users = new ArrayList<>();
        users.add(user);
    }

    @Test
    public void testGetUserById() {
        when(userDao.getUsers()).thenReturn(users);
        User result = userService.getUserById(1);
        assertEquals(user, result);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByIdFail() {
        when(userDao.getUsers()).thenReturn(users);
        userService.getUserById(2);
    }

    @Test
    public void testGetUserByEmail() {
        when(userDao.getUsers()).thenReturn(users);
        User result = userService.getUserByEmail("test@123.abc");
        assertEquals(user, result);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByEmailFail() {
        when(userDao.getUsers()).thenReturn(users);
        userService.getUserByEmail("test1@123.abc");
    }

    @Test
    public void testGetUsersByName() {
        when(userDao.getUsers()).thenReturn(users);
        List<User> result = userService.getUsersByName("test", 1, 1);
        assertEquals(users.size(), result.size());
    }

    @Test
    public void testCreateUser() {
        when(userDao.save(user)).thenReturn(user);
        User result = userService.createUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        when(userDao.save(user)).thenReturn(user);
        User result = userService.updateUser(user);
        assertEquals(user, result);
    }

    @Test
    public void testDeleteUser() {
        when(userDao.delete(anyLong())).thenReturn(true);
        assertTrue(userService.deleteUser(anyLong()));
    }
}
