package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.repository.UserAccountRepository;
import com.epam.core.service.UserAccountService;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({UserAccountService.class, Config.class})
@RunWith(SpringRunner.class)
public class UserAccountServiceIntegrationTest {

    @Autowired
    private UserAccountService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;
    @Autowired
    private UserAccountRepository repository;

    @SneakyThrows
    @Sql(scripts = {"/sql_scripts/insert_users.sql"})
    @Test
    public void testLoadUserAccountsFromFile() {
        service.loadUserAccountsFromFile();
        Assert.assertEquals(3, repository.findAll().spliterator().estimateSize());
    }
}
