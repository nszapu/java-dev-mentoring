package com.epam.core.service;

import com.epam.core.dao.UserDao;
import com.epam.core.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(long userId) {
        for (User user: userDao.getUsers()) {
            if (user.getId() == userId) {
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public User getUserByEmail(String email) {
        for (User user: userDao.getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> result = new ArrayList<>();
        for (User user: userDao.getUsers()) {
            if (user.getName().equals(name)) {
                result.add(user);
            }
        }
        return result;
    }

    public User createUser(User user) {
        userDao.save(user);
        return user;
    }

    public User updateUser(User user) {
        userDao.save(user);
        return user;
    }

    public boolean deleteUser(long userId) {
        return userDao.delete(userId);
    }
}
