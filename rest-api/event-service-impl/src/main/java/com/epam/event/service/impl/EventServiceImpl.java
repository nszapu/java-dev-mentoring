package com.epam.event.service.impl;

import com.epam.event.repository.EventRepository;
import com.epam.event.service.api.EventService;
import com.epam.event.service.dto.Event;
import com.epam.event.service.impl.util.EventToEventEntityConverter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@EnableJpaRepositories(basePackages = "com.epam.event.repository")
@EntityScan(basePackages = "com.epam.event.dao.model")
public class EventServiceImpl implements EventService {

    private EventRepository repository;
    private EventToEventEntityConverter converter;

    public EventServiceImpl(EventRepository repository, EventToEventEntityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Event createEvent(Event event) {
        return converter.convertEventEntityToEvent(repository.save(converter.convertEventToEventEntity(event)));
    }

    @Override
    public Event updateEvent(Event event, Long id) {
        repository.findById(id).orElseThrow(NoSuchElementException::new);
        event.setId(id);
        return converter.convertEventEntityToEvent(repository.save(converter.convertEventToEventEntity(event)));
    }

    @Override
    public Event getEvent(Long id) {
        return converter.convertEventEntityToEvent(repository.findById(id).orElseThrow());
    }

    @Override
    public boolean deleteEvent(Long id) {
        repository.findById(id).orElseThrow(NoSuchElementException::new);;
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll().stream().map(converter::convertEventEntityToEvent).toList();
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        return repository.findAllByTitle(title).stream().map(converter::convertEventEntityToEvent).toList();
    }
}
