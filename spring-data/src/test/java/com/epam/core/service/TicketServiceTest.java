package com.epam.core.service;

import com.epam.core.dao.TicketDao;
import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;
    @InjectMocks
    private TicketService ticketService;
    private Ticket ticket;
    private List<Ticket> tickets;

    @Before
    public void setup() {
        ticket = new TicketEntity();
        ticket.setId(1);
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(1);
        ticket.setCategory(Ticket.Category.PREMIUM);
        tickets = new ArrayList<>();
        tickets.add(ticket);
    }

    @Test
    public void testBookTicket() {
        when(ticketDao.getTickets()).thenReturn(new ArrayList<>());
        when(ticketDao.save(any(Ticket.class))).thenReturn(ticket);
        Ticket result = ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
        assertEquals(ticket, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testBookTicketFail() {
        when(ticketDao.getTickets()).thenReturn(tickets);
        ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
    }

    @Test
    public void testGetBookedTicketsByUser() {
        when(ticketDao.getTickets()).thenReturn(tickets);
        User user = new UserEntity();
        user.setId(1);
        List<Ticket> result = ticketService.getBookedTickets(user, 1, 1);
        assertEquals(tickets.size(), result.size());
    }

    @Test
    public void testGetBookedTicketsByEvent() {
        when(ticketDao.getTickets()).thenReturn(tickets);
        Event event = new EventEntity();
        event.setId(1);
        List<Ticket> result = ticketService.getBookedTickets(event, 1, 1);
        assertEquals(tickets.size(), result.size());
    }

    @Test
    public void testCancelTicket() {
        when(ticketDao.delete(anyLong())).thenReturn(true);
        assertTrue(ticketService.cancelTicket(anyLong()));
    }
}
