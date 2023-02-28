package com.epam.core.service;

import com.epam.core.dao.EventDao;
import com.epam.core.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventService {

    private EventDao eventDao;

    public EventService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public Event getEventById(long eventId) {
        Event event = eventDao.get(eventId);
        log.info("This event was returned: " + event);
        return event;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> result = eventDao.getEvents().stream().filter(event -> event.getTitle().equals(title)).collect(Collectors.toList());
        log.info("These events were returned: " + result);
        return result;
    }

    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        List<Event> result = eventDao.getEvents().stream().filter(event -> event.getDate().equals(day)).collect(Collectors.toList());
        log.info("These events were returned: " + result);
        return result;
    }

    public Event createEvent(Event event) {
        log.info("This event was created: " + event);
        return eventDao.save(event);
    }

    public Event updateEvent(Event event) {
        log.info("This event was updated: " + event);
        return eventDao.save(event);
    }

    public boolean deleteEvent(long eventId) {
        log.info("The event with this id was deleted: " + eventId);
        return eventDao.delete(eventId);
    }
}
