package com.epam.core.service;

import com.epam.core.model.Event;
import com.epam.core.repository.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class EventService {

    @Autowired
    private Storage repository;

    public EventService(Storage repository) {
        this.repository = repository;
    }

    public Event getEventById(long eventId) {
        return (Event) repository.get("event", eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> result = new ArrayList<>();
        for (Event event: repository.getEvents()) {
            if (event.getTitle().equals(title)) {
                result.add(event);
            }
        }
        return result;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> result = new ArrayList<>();
        for (Event event: repository.getEvents()) {
            if (event.getDate().equals(day)) {
                result.add(event);
            }
        }
        return result;
    }

    public Event createEvent(Event event) {
        repository.save("event", event);
        return event;
    }

    public Event updateEvent(Event event) {
        repository.save("event", event);
        return event;
    }

    public boolean deleteEvent(long eventId) {
        return repository.delete("event", eventId);
    }
}
