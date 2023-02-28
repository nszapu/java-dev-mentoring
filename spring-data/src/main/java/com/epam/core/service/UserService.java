package com.epam.core.service;

import com.epam.core.dao.UserDao;
import com.epam.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(long userId) {
        for (User user: userDao.getUsers()) {
            if (user.getId() == userId) {
                log.info("This user was returned: " + user);
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public User getUserByEmail(String email) {
        for (User user: userDao.getUsers()) {
            if (user.getEmail().equals(email)) {
                log.info("This user was returned: " + user);
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> result = userDao.getUsers().stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        log.info("These users were returned: " + result);
        return result;
    }

    public User createUser(User user) {
        log.info("This user was created: " + user);
        return userDao.save(user);
    }

    public User updateUser(User user) {
        log.info("This user was updated: " + user);
        return userDao.save(user);
    }

    public boolean deleteUser(long userId) {
        log.info("The user with this id was deleted: " + userId);
        return userDao.delete(userId);
    }
}
