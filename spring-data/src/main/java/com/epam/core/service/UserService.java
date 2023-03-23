package com.epam.core.service;

import com.epam.core.dto.UserDto;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.User;
import com.epam.core.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Value("${users.path}")
    private String usersPath;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;

    public void loadUsersFromFile() throws IOException {
        List<UserDto> users = Arrays.asList(mapper.readValue(new File(classLoader.getResource(usersPath).getFile()), UserDto[].class));
        users.forEach(user -> repository.save(convertUserDtoToEntity(user)));
    }

    public User getUserById(long userId) {
        return convertUserEntityToDto(repository.findById(userId).orElseThrow());
    }

    public User getUserByEmail(String email) {
        return convertUserEntityToDto(repository.findByEmail(email).orElseThrow());
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> result = repository.findByName(name, PageRequest.of(pageNum, pageSize)).stream().map(this::convertUserEntityToDto).collect(Collectors.toList());
        log.debug("These users were returned: " + result);
        return result;
    }

    public User createUser(User user) {
        log.debug("Creating user: " + user);
        UserEntity userEntity = convertUserDtoToEntity(user);
        User result = convertUserEntityToDto(repository.save(userEntity));
        log.info("User was created: {}", result);
        return result;
    }

    public User updateUser(User user) {
        log.debug("Updating user: " + user);
        UserEntity userEntity = convertUserDtoToEntity(user);
        User result = convertUserEntityToDto(repository.save(userEntity));
        log.info("User was updated: {}", result);
        return result;
    }

    public boolean deleteUser(long userId) {
        log.debug("Deleting user with id: " + userId);
        repository.deleteById(userId);
        return !repository.existsById(userId);
    }

    private User convertUserEntityToDto(UserEntity userEntity) {
        User user = new UserDto();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    private UserEntity convertUserDtoToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
