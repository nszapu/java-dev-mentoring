package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.repository.TicketRepository;
import com.epam.core.service.TicketService;
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
@Import({TicketService.class, Config.class})
@RunWith(SpringRunner.class)
public class TicketServiceIntegrationTest {

    @Autowired
    private TicketService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;
    @Autowired
    private TicketRepository repository;

    @SneakyThrows
    @Sql(scripts = {"/sql_scripts/insert_users.sql", "/sql_scripts/insert_events.sql"})
    @Test
    public void testLoadTicketsFromFile() {
        service.loadTicketsFromFile();
        Assert.assertEquals(3, repository.findAll().spliterator().estimateSize());
    }
}
