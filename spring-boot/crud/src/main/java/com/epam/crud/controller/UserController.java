package com.epam.crud.controller;

import com.epam.crud.model.User;
import com.epam.crud.model.UserResponse;
import com.epam.crud.service.UserService;
import com.epam.crud.util.UserToUserResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.crud.util.UserToUserResponseConverter.convertToUserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserResponse> getUsers() {
        return service.getUsers()
                .stream()
                .map(UserToUserResponseConverter::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
        return new ResponseEntity<>(convertToUserResponse(service.getUser(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return new ResponseEntity<>(convertToUserResponse(service.save(user)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(convertToUserResponse(service.save(user)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        service.getUser(id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
