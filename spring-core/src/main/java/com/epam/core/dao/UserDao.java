package com.epam.core.dao;

import com.epam.core.Application;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {

    @Getter
    @Setter
    private String usersPath;
    private List<User> users = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    ClassLoader classLoader = Application.class.getClassLoader();
    @Getter
    @Setter
    private Storage repository;

    public void loadUsersFromFile() throws IOException {
        users = Arrays.asList(mapper.readValue(new File(classLoader.getResource(usersPath).getFile()), UserEntity[].class));
        users.forEach(event -> repository.save("user", event));
    }

    public User get(long id) {
        return (User) repository.get("user", id);
    }

    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        for (Object key: repository.getKeyArray()){
            if (key.toString().startsWith("user")){
                result.add((User) repository.get(key.toString()));
            }
        }
        return result;
    }

    public void save(User user) {
        repository.save("user", user);
    }

    public boolean delete(long id) {
        return repository.delete("user", id);
    }
}
