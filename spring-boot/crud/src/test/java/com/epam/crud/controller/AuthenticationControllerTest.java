package com.epam.crud.controller;

import com.epam.crud.model.AuthenticationRequest;
import com.epam.crud.model.RegisterRequest;
import com.epam.crud.model.Token;
import com.epam.crud.service.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthenticationService service;

    @SneakyThrows
    @Test
    public void register() {
//        given
        RegisterRequest request = new RegisterRequest();
        request.setName("lala");
        request.setEmail("lala@email.com");
        request.setPassword("password");
        Token token = new Token();
        token.setUserId(1);
        token.setAccessToken("test1");
        token.setRefreshToken("test2");
        when(service.register(request)).thenReturn(token);
        String body = "{\n" +
                "    \"name\": \"lala\",\n" +
                "    \"email\": \"lala@email.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        String expected = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"accessToken\": \"test1\",\n" +
                "    \"refreshToken\": \"test2\"\n" +
                "}";
//        when
        mvc.perform(post("/api/auth/register")
                    .with(jwt())
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
//        then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expected));
    }

    @SneakyThrows
    @Test
    public void registerEmailExists() {
//        given
        RegisterRequest request = new RegisterRequest();
        request.setName("lala");
        request.setEmail("lala@email.com");
        request.setPassword("password");
        when(service.register(request)).thenThrow(new EntityExistsException());
        String body = """
                {
                    "name": "lala",
                    "email": "lala@email.com",
                    "password": "password"
                }""";
        String expected = "{\"statusCode\":400,\"message\":null,\"description\":\"uri=/api/auth/register\"}";
//        when
        mvc.perform(post("/api/auth/register")
                        .with(jwt())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
//        then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expected));
    }

    @SneakyThrows
    @Test
    public void authenticate() {
//        given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("lala@email.com");
        request.setPassword("password");
        Token token = new Token();
        token.setUserId(1);
        token.setAccessToken("test1");
        token.setRefreshToken("test2");
        when(service.authenticate(request)).thenReturn(token);
        String body = """
                {
                    "email": "lala@email.com",
                    "password": "password"
                }""";
        String expected = """
                {
                    "userId": 1,
                    "accessToken": "test1",
                    "refreshToken": "test2"
                }""";
//        when
        mvc.perform(post("/api/auth/authenticate")
                        .with(jwt())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
//        then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}
