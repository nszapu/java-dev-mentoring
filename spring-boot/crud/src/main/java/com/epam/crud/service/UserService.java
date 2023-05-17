package com.epam.crud.service;

import com.epam.crud.model.User;
import com.epam.crud.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(long id) {
        User user = repository.findById(id).orElseThrow();
        log.info("User was returned with email: {}.", user.getEmail());
        return user;
    }

    public List<User> getUsers() {
        List<User> users = repository.findAll();
        log.info("{} users were returned.", users.size());
        return users;
    }

    public User save(User user) {
        User savedUser = repository.save(user);
        log.info("User was saved with email: {}.", savedUser.getEmail());
        return savedUser;
    }

    public void delete(long id) {
        repository.deleteById(id);
        log.info("User was deleted with id: {}.", id);
    }
}
