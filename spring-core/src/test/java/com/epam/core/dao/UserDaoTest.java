package com.epam.core.dao;

import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import lombok.SneakyThrows;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock
    private Storage repository;
    @InjectMocks
    private UserDao userDao;
    private User user;
    private List<User> users;

    @SneakyThrows
    @Before
    public void setup() {
        user = new UserEntity();
        user.setId(123);
        user.setName("test");
        user.setEmail("test@123.abc");
        users = new ArrayList<>();
        users.add(user);
    }

//    @Test
//    public void testLoadUsersFromFile() {
//        when(Arrays.asList(any(UserEntity[].class))).thenReturn(new ArrayList<>());
//    }

    @Test
    public void testGet() {
        when(repository.get("user", 1)).thenReturn(user);
        assertEquals(user, userDao.get(1));
    }

    @Test
    public void testGetUsers() {
        when(repository.getKeyArray()).thenReturn(new Object[] {"user:123"});
        when(repository.get("user:123")).thenReturn(user);
        assertEquals(users.size(), userDao.getUsers().size());
    }

    @Test
    public void testSave() {
        when(repository.save(anyString(), any(User.class))).thenReturn(user);
        assertEquals(user, userDao.save(user));
    }

    @Test
    public void testDelete() {
        when(repository.delete(anyString(), anyLong())).thenReturn(true);
        assertTrue(userDao.delete(1));
    }
}
