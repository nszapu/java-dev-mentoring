package com.epam.core.service;

import com.epam.core.dao.TicketDao;
import com.epam.core.entity.TicketEntity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketService {

    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        for (Ticket ticket: ticketDao.getTickets()) {
            if (ticket.getEventId() == eventId && ticket.getPlace() == place) {
                throw new IllegalStateException("This place is already booked");
            }
        }
        Ticket ticket = new TicketEntity();
        ticket.setId(ticketDao.getTickets().size() + 1);
        ticket.setEventId(eventId);
        ticket.setUserId(userId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        log.info("This ticket was booked: " + ticket);
        return ticketDao.save(ticket);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> result = ticketDao.getTickets().stream().filter(ticket -> ticket.getUserId() == user.getId()).collect(Collectors.toList());
        log.info("These tickets were returned: " + result);
        return result;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> result = ticketDao.getTickets().stream().filter(ticket -> ticket.getEventId() == event.getId()).collect(Collectors.toList());
        log.info("These tickets were returned: " + result);
        return result;
    }

    public boolean cancelTicket(long ticketId) {
        log.info("The ticket with this id was canceled: " + ticketId);
        return ticketDao.delete(ticketId);
    }
}
