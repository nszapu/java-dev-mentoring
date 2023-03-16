package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.repository.UserRepository;
import com.epam.core.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({UserService.class, Config.class})
@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;
    @Autowired
    private UserRepository repository;

    @SneakyThrows
    @Test
    public void testLoadUsersFromFile() {
        service.loadUsersFromFile();
        Assert.assertEquals(3, repository.findAll().spliterator().estimateSize());
    }
}
