package com.epam.core.integration;

import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.facade.BookingFacade;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.service.BookingService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Collections;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application_context.xml");
    private BookingFacade bookingFacade = applicationContext.getBean(BookingService.class);

    @SneakyThrows
    @Test
    public void testBookTicket() {
        // given
        User user = new UserEntity();
        user.setId(4);
        user.setName("test_user");
        user.setEmail("test@email.com");
        bookingFacade.createUser(user);
        Event event = new EventEntity();
        event.setId(4);
        event.setTitle("Concert4");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-04-01"));
        bookingFacade.createEvent(event);
        // when
        Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        // then
        Ticket expected = new TicketEntity();
        expected.setId(4);
        expected.setCategory(Ticket.Category.PREMIUM);
        expected.setPlace(1);
        expected.setEventId(4);
        expected.setUserId(4);
        assertEquals(expected, ticket);
    }

    @Test
    public void testCancelTicket() {
        assertEquals(1, bookingFacade.getBookedTickets(bookingFacade.getUserById(3), 1, 1).size());
        bookingFacade.cancelTicket(3);
        assertEquals(Collections.EMPTY_LIST, bookingFacade.getBookedTickets(bookingFacade.getUserById(3), 1, 1));
    }
}
