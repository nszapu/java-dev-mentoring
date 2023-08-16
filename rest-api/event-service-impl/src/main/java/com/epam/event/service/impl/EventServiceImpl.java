package com.epam.event.service.impl;

import com.epam.event.repository.EventRepository;
import com.epam.event.service.api.EventService;
import com.epam.event.service.dto.Event;
import com.epam.event.service.impl.util.EventToEventEntityConverter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.event.service.impl.util.EventToEventEntityConverter.convertEventEntityToEvent;
import static com.epam.event.service.impl.util.EventToEventEntityConverter.convertEventToEventEntity;

@Service
@EnableJpaRepositories(basePackages = "com.epam.event.repository")
@EntityScan(basePackages = "com.epam.event.dao.model")
public class EventServiceImpl implements EventService {

    private EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event createEvent(Event event) {
        return convertEventEntityToEvent(repository.save(convertEventToEventEntity(event)));
    }

    @Override
    public Event updateEvent(Event event) {
        return convertEventEntityToEvent(repository.save(convertEventToEventEntity(event)));
    }

    @Override
    public Event getEvent(Long id) {
        return convertEventEntityToEvent(repository.findById(id).orElseThrow());
    }

    @Override
    public boolean deleteEvent(Long id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll().stream().map(EventToEventEntityConverter::convertEventEntityToEvent).toList();
    }

    @Override
    public List<Event> getAllEventsByTitle(String title) {
        return repository.findAllByTitle(title).stream().map(EventToEventEntityConverter::convertEventEntityToEvent).toList();
    }
}
