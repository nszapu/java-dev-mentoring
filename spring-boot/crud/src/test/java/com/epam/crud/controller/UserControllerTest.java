package com.epam.crud.controller;

import com.epam.crud.model.User;
import com.epam.crud.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;
    private User user1;
    private List<User> users;

    @BeforeEach
    public void setup() {
        user1 = new User(1, "lala", "lala@email.com", "password");
        User user2 = new User(2, "lolo", "lolo@email.com", "password");
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @SneakyThrows
    @Test
    public void getUsers() {
        when(service.getUsers()).thenReturn(users);
        mvc.perform(get("/api/users")
                        .with(jwt())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("lala"))
                .andExpect(jsonPath("$[0].email").value("lala@email.com"))
                .andExpect(jsonPath("$[1].name").value("lolo"))
                .andExpect(jsonPath("$[1].email").value("lolo@email.com"));
    }

    @SneakyThrows
    @Test
    public void getUsersNotAuthorized() {
        mvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    public void getUser() {
        when(service.getUser(1)).thenReturn(user1);
        mvc.perform(get("/api/users/1")
                        .with(jwt())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("lala"))
                .andExpect(jsonPath("$.email").value("lala@email.com"));
    }

    @SneakyThrows
    @Test
    public void getUserNotFound() {
        when(service.getUser(99)).thenThrow(new NoSuchElementException());
        mvc.perform(get("/api/users/99")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.description").value("uri=/api/users/99"));
    }

    @SneakyThrows
    @Test
    public void createUser() {
        when(service.save(any(User.class))).thenReturn(user1);
        String body = "{\"name\":\"lala\",\"email\":\"lala@email.com\"}";
        mvc.perform(post("/api/users")
                        .with(jwt())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("lala"))
                .andExpect(jsonPath("$.email").value("lala@email.com"));
    }

    @SneakyThrows
    @Test
    public void updateUser() {
        when(service.save(any(User.class))).thenReturn(user1);
        String body = "{\"id\":1,\"name\":\"lala\",\"email\":\"lala@email.com\"}";
        mvc.perform(put("/api/users")
                        .with(jwt())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("lala"))
                .andExpect(jsonPath("$.email").value("lala@email.com"));
    }

    @SneakyThrows
    @Test
    public void deleteUser() {
        when(service.getUser(1)).thenReturn(user1);
        mvc.perform(delete("/api/users/1")
                        .with(jwt()))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void deleteUserNotFound() {
        when(service.getUser(1)).thenThrow(new NoSuchElementException());
        mvc.perform(delete("/api/users/1")
                        .with(jwt()))
                .andExpect(status().isNotFound());
    }
}
