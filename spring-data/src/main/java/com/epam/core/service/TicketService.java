package com.epam.core.service;

import com.epam.core.dto.TicketDto;
import com.epam.core.entity.EventEntity;
import com.epam.core.entity.TicketEntity;
import com.epam.core.entity.UserAccountEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.EventRepository;
import com.epam.core.repository.TicketRepository;
import com.epam.core.repository.UserAccountRepository;
import com.epam.core.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketService {

    @Value("${tickets.path}")
    private String ticketsPath;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;

    public void loadTicketsFromFile() throws IOException {
        List<TicketDto> tickets = Arrays.asList(mapper.readValue(new File(classLoader.getResource(ticketsPath).getFile()), TicketDto[].class));
        tickets.forEach(ticket -> ticketRepository.save(convertTicketDtoToEntity(ticket)));
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        EventEntity event = eventRepository.findById(eventId).orElseThrow();
        UserAccountEntity account = userAccountRepository.findByUserId(userId).orElseThrow();
        if (event.getTicketPrice().compareTo(account.getBalance()) > 0) {
            throw new IllegalStateException("User does not have sufficient balance to book the ticket");
        }
        if (!ticketRepository.findByEventIdAndPlace(eventId, place).isEmpty()) {
            throw new IllegalStateException("Place is already booked");
        }
        BigDecimal currentBalance = account.getBalance();
        account.setBalance(currentBalance.subtract(event.getTicketPrice()));
        userAccountRepository.save(account);
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setEvent(event);
        ticketEntity.setUser(userRepository.findById(userId).orElseThrow());
        ticketEntity.setCategory(category.toString());
        ticketEntity.setPlace(place);
        return convertTicketEntityToDto(ticketRepository.save(ticketEntity));
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> result = ticketRepository.findByUserId(user.getId(), PageRequest.of(pageNum, pageSize)).stream().map(this::convertTicketEntityToDto).collect(Collectors.toList());
        log.info("Tickets were returned: {}", result);
        return result;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> result = ticketRepository.findByEventId(event.getId(), PageRequest.of(pageNum, pageSize)).stream().map(this::convertTicketEntityToDto).collect(Collectors.toList());
        log.info("Tickets were returned: {}", result);
        return result;
    }

    public boolean cancelTicket(long ticketId) {
        ticketRepository.deleteById(ticketId);
        log.info("Ticket with this id was canceled: {}", ticketId);
        return !ticketRepository.existsById(ticketId);
    }

    private Ticket convertTicketEntityToDto(TicketEntity ticketEntity) {
        Ticket ticket = new TicketDto();
        ticket.setId(ticketEntity.getId());
        ticket.setEventId(ticketEntity.getEvent().getId());
        ticket.setUserId(ticketEntity.getUser().getId());
        ticket.setCategory(Ticket.Category.valueOf(ticketEntity.getCategory()));
        ticket.setPlace(ticketEntity.getPlace());
        return ticket;
    }

    private TicketEntity convertTicketDtoToEntity(TicketDto ticket) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(ticket.getId());
        ticketEntity.setEvent(eventRepository.findById(ticket.getEventId()).orElseThrow());
        ticketEntity.setUser(userRepository.findById(ticket.getUserId()).orElseThrow());
        ticketEntity.setCategory(ticket.getCategory().toString());
        ticketEntity.setPlace(ticket.getPlace());
        return ticketEntity;
    }
}
