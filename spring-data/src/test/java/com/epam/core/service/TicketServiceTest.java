package com.epam.core.service;

import com.epam.core.dto.EventDto;
import com.epam.core.dto.TicketDto;
import com.epam.core.dto.UserDto;
import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserAccountEntity;
import com.epam.core.entity.UserEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.EventRepository;
import com.epam.core.repository.TicketRepository;
import com.epam.core.repository.UserAccountRepository;
import com.epam.core.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @InjectMocks
    private TicketService ticketService;
    private Ticket ticket;
    private List<Ticket> tickets;
    private TicketEntity ticketEntity;
    private List<TicketEntity> ticketEntities;
    private EventEntity event;
    private UserAccountEntity account;
    private UserEntity user;
    private PageRequest pageRequest;

    @SneakyThrows
    @Before
    public void setup() {
        ticket = new TicketDto();
        ticket.setId(1);
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(1);
        ticket.setCategory(Ticket.Category.PREMIUM);
        tickets = new ArrayList<>();
        tickets.add(ticket);
        ticketEntity = new TicketEntity();
        ticketEntity.setId(1);
        user = new UserEntity();
        user.setId(1);
        ticketEntity.setUser(user);
        event = new EventEntity();
        event.setId(1);
        event.setTitle("test");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-01-01"));
        event.setTicketPrice(BigDecimal.valueOf(100));
        ticketEntity.setEvent(event);
        ticketEntity.setPlace(1);
        ticketEntity.setCategory("PREMIUM");
        ticketEntities = new ArrayList<>();
        ticketEntities.add(ticketEntity);
        account = new UserAccountEntity();
        account.setId(1);
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(1000));
        pageRequest = PageRequest.of(1,1);
    }

    @Test
    public void testBookTicket() {
        when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(event));
        when(userAccountRepository.findByUserId(1)).thenReturn(Optional.ofNullable(account));
        when(ticketRepository.findByEventIdAndPlace(1, 1)).thenReturn(Collections.EMPTY_LIST);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);
        Ticket result = ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
        assertEquals(ticket, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testBookTicketFail() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTicketPrice(BigDecimal.valueOf(1000));
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setBalance(BigDecimal.valueOf(999));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));
        when(userAccountRepository.findByUserId(1)).thenReturn(Optional.of(userAccountEntity));
        ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
    }

    @Test(expected = IllegalStateException.class)
    public void testBookTicketFail2() {
        when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(event));
        when(userAccountRepository.findByUserId(1)).thenReturn(Optional.ofNullable(account));
        when(ticketRepository.findByEventIdAndPlace(1, 1)).thenReturn(ticketEntities);
        ticketService.bookTicket(1, 1, 1, Ticket.Category.PREMIUM);
    }

    @Test
    public void testGetBookedTicketsByUser() {
        when(ticketRepository.findByUserId(1, pageRequest)).thenReturn(ticketEntities);
        User user = new UserDto();
        user.setId(1);
        List<Ticket> result = ticketService.getBookedTickets(user, 1, 1);
        assertEquals(tickets, result);
    }

    @Test
    public void testGetBookedTicketsByEvent() {
        when(ticketRepository.findByEventId(1, pageRequest)).thenReturn(ticketEntities);
        Event event = new EventDto();
        event.setId(1);
        List<Ticket> result = ticketService.getBookedTickets(event, 1, 1);
        assertEquals(tickets, result);
    }

    @Test
    public void testCancelTicket() {
        ticketService.cancelTicket(1);
        verify(ticketRepository).deleteById(anyLong());
        verify(ticketRepository).existsById(anyLong());
    }
}
