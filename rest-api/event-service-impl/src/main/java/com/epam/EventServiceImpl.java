package com.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Override
    public Event createEvent(Event event) {
        return repository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return repository.save(event);
    }

    @Override
    public Event getEvent(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public boolean deleteEvent(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        return repository.findAllByTitle(title);
    }
}
