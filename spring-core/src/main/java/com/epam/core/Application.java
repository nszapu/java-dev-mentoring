package com.epam.core;

import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import com.epam.core.service.BookingService;
import com.epam.core.service.EventService;
import com.epam.core.service.TicketService;
import com.epam.core.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = Application.class.getClassLoader();
        Storage storage = new Storage();

        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File(classLoader.getResource("events.json").getFile()), EventEntity[].class));
            List<Ticket> tickets = Arrays.asList(mapper.readValue(new File(classLoader.getResource("tickets.json").getFile()), TicketEntity[].class));
            List<User> users = Arrays.asList(mapper.readValue(new File(classLoader.getResource("users.json").getFile()), UserEntity[].class));

            for (Event event: events) {
                storage.save("event", event);
            }
            for (Ticket ticket: tickets) {
                storage.save("ticket", ticket);
            }
            for (User user: users) {
                storage.save("user", user);
            }

            System.out.println(storage);

            EventService eventService = new EventService(storage);
            TicketService ticketService = new TicketService(storage);
            UserService userService = new UserService(storage);
            BookingService bookingService = new BookingService(eventService, ticketService, userService);

            System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"));

            System.out.println(bookingService.getEventsForDay(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"), 1, 1));

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
