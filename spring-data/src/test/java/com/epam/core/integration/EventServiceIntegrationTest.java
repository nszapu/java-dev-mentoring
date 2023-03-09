package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.cofig.TestConfig;
import com.epam.core.dto.EventDto;
import com.epam.core.dto.TicketDto;
import com.epam.core.dto.UserDto;
import com.epam.core.facade.BookingFacade;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({TestConfig.class})
@ExtendWith(SpringExtension.class)
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;

    @SneakyThrows
    @Test
    public void testLoadEventsFromFile() {
        eventService.loadEventsFromFile();
    }
}
