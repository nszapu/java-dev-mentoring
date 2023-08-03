package com.repository;

import com.jmp.dto.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User, Integer> {

    private List<User> users = new ArrayList<>();

    @Override
    public User get(Integer key) {
        return users.get(key);
    }

    @Override
    public List<User> getAll() {
        return users.stream().toList();
    }

    @Override
    public void save(User item) {
        users.add(item);
    }

    @Override
    public User delete(Integer key) {
        return users.remove(key.intValue());
    }
}
