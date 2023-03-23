package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.repository.EventRepository;
import com.epam.core.service.EventService;
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

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({EventService.class, Config.class})
@RunWith(SpringRunner.class)
public class EventServiceIntegrationTest {

    @Autowired
    private EventService service;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;
    @Autowired
    private EventRepository repository;

    @SneakyThrows
    @Test
    public void testLoadEventsFromFile() {
        service.loadEventsFromFile();
        Assert.assertEquals(3, repository.findAll().spliterator().estimateSize());
    }
}
