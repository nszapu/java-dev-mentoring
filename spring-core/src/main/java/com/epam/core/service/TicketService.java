package com.epam.core.service;

import com.epam.core.entity.TicketEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import com.epam.core.repository.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private Storage repository;

    public TicketService(Storage repository) {
        this.repository = repository;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        for (Ticket ticket: repository.getTickets()) {
            if (ticket.getEventId() == eventId && ticket.getPlace() == place) {
                throw new IllegalStateException("This place is already booked");
            }
        }
        Ticket ticket = new TicketEntity();
        ticket.setId(123);
        ticket.setEventId(eventId);
        ticket.setUserId(userId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        return ticket;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket: repository.getTickets()) {
            if (ticket.getUserId() == user.getId()) {
                result.add(ticket);
            }
        }
        return result;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket: repository.getTickets()) {
            if (ticket.getEventId() == event.getId()) {
                result.add(ticket);
            }
        }
        return result;
    }

    public boolean cancelTicket(long ticketId) {
        return repository.delete("ticket", ticketId);
    }
}
