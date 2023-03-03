package com.epam.core.integration;

import com.epam.core.dto.EventDto;
import com.epam.core.dto.TicketDto;
import com.epam.core.dto.UserDto;
import com.epam.core.facade.BookingFacade;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class IntegrationTest {

    @Autowired
    private BookingFacade bookingFacade;

    @SneakyThrows
    @Test
    public void testBookTicket() {
        // given
        User user = new UserDto();
        user.setId(4);
        user.setName("test_user");
        user.setEmail("test@email.com");
        bookingFacade.createUser(user);
        Event event = new EventDto();
        event.setId(4);
        event.setTitle("Concert4");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-04-01"));
        bookingFacade.createEvent(event);
        // when
        Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        // then
        Ticket expected = new TicketDto();
        expected.setId(4);
        expected.setCategory(Ticket.Category.PREMIUM);
        expected.setPlace(1);
        expected.setEventId(4);
        expected.setUserId(4);
        assertEquals(expected, ticket);
    }
//
//    @Test
//    public void testCancelTicket() {
//        assertEquals(1, bookingFacade.getBookedTickets(bookingFacade.getUserById(3), 1, 1).size());
//        bookingFacade.cancelTicket(3);
//        assertEquals(Collections.EMPTY_LIST, bookingFacade.getBookedTickets(bookingFacade.getUserById(3), 1, 1));
//    }
}
