package com.epam.core.dao;

import com.epam.core.entity.TicketEntity;
import com.epam.core.model.Ticket;
import com.epam.core.repository.Storage;
import lombok.SneakyThrows;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketDaoTest {

    @Mock
    private Storage repository;
    @InjectMocks
    private TicketDao ticketDao;
    private Ticket ticket;
    private List<Ticket> tickets;

    @SneakyThrows
    @Before
    public void setup() {
        ticket = new TicketEntity();
        ticket.setId(123);
        ticket.setEventId(123);
        ticket.setUserId(123);
        ticket.setPlace(123);
        ticket.setCategory(Ticket.Category.PREMIUM);
        tickets = new ArrayList<>();
        tickets.add(ticket);
    }

//    @Test
//    public void testLoadTicketsFromFile() {
//        when(Arrays.asList(any(TicketEntity[].class))).thenReturn(new ArrayList<>());
//    }

    @Test
    public void testGetTickets() {
        when(repository.getKeyArray()).thenReturn(new Object[] {"ticket:123"});
        when(repository.get("ticket:123")).thenReturn(ticket);
        assertEquals(tickets.size(), ticketDao.getTickets().size());
    }

    @Test
    public void testSave() {
        when(repository.save(anyString(), any(Ticket.class))).thenReturn(ticket);
        assertEquals(ticket, ticketDao.save(ticket));
    }

    @Test
    public void testDelete() {
        when(repository.delete(anyString(), anyLong())).thenReturn(true);
        assertTrue(ticketDao.delete(1));
    }
}
