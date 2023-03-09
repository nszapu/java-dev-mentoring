package com.epam.core.service;

import com.epam.core.dto.EventDto;
import com.epam.core.entity.EventEntity;
import com.epam.core.model.Event;
import com.epam.core.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventService {

    @Value("${events.path}")
    private String eventsPath;
    @Autowired
    private EventRepository repository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ClassLoader classLoader;

    public void loadEventsFromFile() throws IOException {
        List<EventEntity> events = Arrays.asList(mapper.readValue(new File(classLoader.getResource(eventsPath).getFile()), EventEntity[].class));
        events.forEach(event -> repository.save(event));
    }

    public Event getEventById(long eventId) {
        EventEntity eventEntity = repository.findById(eventId).orElseThrow();
        Event event = convertEventEntityToDto(eventEntity);
        log.info("Event was returned: {}", event);
        return event;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> result = repository.findByTitle(title, PageRequest.of(pageNum, pageSize)).stream().map(this::convertEventEntityToDto).collect(Collectors.toList());
        log.info("Events were returned: {}", result);
        return result;
    }

    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        List<Event> result = repository.findByDate(day, PageRequest.of(pageNum, pageSize)).stream().map(this::convertEventEntityToDto).collect(Collectors.toList());
        log.info("Events were returned: {}", result);
        return result;
    }

    public Event createEvent(Event event) {
        EventEntity eventEntity = convertEventDtoToEntity(event);
        Event result = convertEventEntityToDto(repository.save(eventEntity));
        log.info("Event was created: {}", result);
        return result;
    }

    public Event updateEvent(Event event) {
        EventEntity eventEntity = convertEventDtoToEntity(event);
        Event result = convertEventEntityToDto(repository.save(eventEntity));
        log.info("Event was updated: {}", result);
        return result;
    }

    public boolean deleteEvent(long eventId) {
        repository.deleteById(eventId);
        log.info("Event with id was deleted: {}", eventId);
        return !repository.existsById(eventId);
    }

    private Event convertEventEntityToDto(EventEntity eventEntity) {
        Event event = new EventDto();
        event.setId(eventEntity.getId());
        event.setTitle(eventEntity.getTitle());
        event.setDate(eventEntity.getDate());
        event.setTicketPrice(eventEntity.getTicketPrice());
        return event;
    }

    private EventEntity convertEventDtoToEntity(Event event) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(event.getId());
        eventEntity.setTitle(event.getTitle());
        eventEntity.setDate(event.getDate());
        eventEntity.setTicketPrice(event.getTicketPrice());
        return eventEntity;
    }
}
