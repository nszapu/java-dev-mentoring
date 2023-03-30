package com.epam.crud.service;

import com.epam.crud.model.User;
import com.epam.crud.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}