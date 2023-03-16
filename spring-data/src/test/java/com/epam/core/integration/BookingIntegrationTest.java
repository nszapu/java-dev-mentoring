package com.epam.core.integration;

import com.epam.core.cofig.Config;
import com.epam.core.dto.EventDto;
import com.epam.core.dto.UserAccountDto;
import com.epam.core.dto.UserDto;
import com.epam.core.facade.BookingFacade;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.model.UserAccount;
import com.epam.core.service.*;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({BookingService.class, EventService.class, UserService.class, TicketService.class, UserAccountService.class, Config.class})
@RunWith(SpringRunner.class)
public class BookingIntegrationTest {

    @Autowired
    private BookingFacade bookingFacade;
    @Autowired
    private SimpleDateFormat dateFormat;

    @SneakyThrows
    @Test
    public void testBookingProcess() {
//        given
        User user = new UserDto();
        user.setName("Norbi");
        user.setEmail("norbi@epam.com");
        user = bookingFacade.createUser(user);

        UserAccount userAccount = new UserAccountDto();
        userAccount.setBalance(BigDecimal.valueOf(199));
        userAccount.setUserId(user.getId());
        bookingFacade.createUserAccount(userAccount);

        Event event = new EventDto();
        event.setTitle("RHCP Concert");
        event.setDate(dateFormat.parse("2023-04-21"));
        event.setTicketPrice(BigDecimal.valueOf(200));
        event = bookingFacade.createEvent(event);

//        when
        try {
            bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        userAccount = bookingFacade.refillUserAccountBalance(user, BigDecimal.valueOf(200));
        bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);

//        then
        assertEquals(userAccount.getBalance().subtract(event.getTicketPrice()), bookingFacade.getUserAccountByUserId(user.getId()).getBalance());
    }

    @SneakyThrows
    @Test(expected = IllegalStateException.class)
    public void testPlaceBooked() {
//        given
        User user = new UserDto();
        user.setName("Norbi");
        user.setEmail("norbi@epam.com");
        user = bookingFacade.createUser(user);

        UserAccount userAccount = new UserAccountDto();
        userAccount.setBalance(BigDecimal.valueOf(1000));
        userAccount.setUserId(user.getId());
        bookingFacade.createUserAccount(userAccount);

        Event event = new EventDto();
        event.setTitle("RHCP Concert");
        event.setDate(dateFormat.parse("2023-04-21"));
        event.setTicketPrice(BigDecimal.valueOf(200));
        event = bookingFacade.createEvent(event);

//        when
        bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
//        then
//        IllegalStateException: Place is already booked
    }
}
