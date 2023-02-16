package com.epam.core.service;

import com.epam.core.dao.EventDao;
import com.epam.core.model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private EventDao eventDao;

    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public Event getEventById(long eventId) {
        return eventDao.get(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> result = new ArrayList<>();
        for (Event event: eventDao.getEvents()) {
            if (event.getTitle().equals(title)) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> result = new ArrayList<>();
        for (Event event: eventDao.getEvents()) {
            if (event.getDate().equals(day)) {
                result.add(event);
            }
        }
        return result;
    }

    public Event createEvent(Event event) {
        eventDao.save(event);
        return event;
    }

    public Event updateEvent(Event event) {
        eventDao.save(event);
        return event;
    }

    public boolean deleteEvent(long eventId) {
        return eventDao.delete(eventId);
    }
}
