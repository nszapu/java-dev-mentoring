package com.epam.core.service;

import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private Storage repository;

    public UserService(Storage repository) {
        this.repository = repository;
    }

    public User getUserById(long userId) {
        for (User user: repository.getUsers()) {
            if (user.getId() == userId) {
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public User getUserByEmail(String email) {
        for (User user: repository.getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
//        throw new UserNotFoundException();
        throw new RuntimeException();
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> result = new ArrayList<>();
        for (User user: repository.getUsers()) {
            if (user.getName().equals(name)) {
                result.add(user);
            }
        }
        return result;
    }

    public User createUser(User user) {
        repository.save("user", user);
        return user;
    }

    public User updateUser(User user) {
        repository.save("user", user);
        return user;
    }

    public boolean deleteUser(long userId) {
        return repository.delete("user", userId);
    }
}
