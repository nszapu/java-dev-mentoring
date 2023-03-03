package com.epam.core.service;

import com.epam.core.dto.EventDto;
import com.epam.core.entity.EventEntity;
import com.epam.core.model.Event;
import com.epam.core.repository.EventRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepository repository;
    @InjectMocks
    private EventService eventService;
    private EventEntity eventEntity;
    private Event event;
    private SimpleDateFormat dateFormat;
    private List<EventEntity> eventEntities;
    private List<Event> events;

    @SneakyThrows
    @Before
    public void setup() {
        eventEntity = new EventEntity();
        eventEntity.setId(123);
        eventEntity.setTitle("test");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        eventEntity.setDate(dateFormat.parse("2023-01-01"));
        eventEntities = new ArrayList<>();
        eventEntities.add(eventEntity);
        event = new EventDto();
        event.setId(123);
        event.setTitle("test");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-01-01"));
        events = new ArrayList<>();
        events.add(event);
    }

    @Test
    public void testLoadEventsFromFile() {

    }

    @Test
    public void testGetEventById() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(eventEntity));
        assertEquals(event, eventService.getEventById(anyLong()));
    }

    @Test
    public void testGetEventsByTitle() {
        when(repository.findByTitle(anyString())).thenReturn(eventEntities);
        List<Event> result = eventService.getEventsByTitle("test", 1, 1);
        assertEquals(events, result);
    }

    @SneakyThrows
    @Test
    public void testGetEventsByDay() {
        when(repository.findByDate(any(Date.class))).thenReturn(eventEntities);
        List<Event> result = eventService.getEventsByDay(dateFormat.parse("2023-01-01"), 1, 1);
        assertEquals(events, result);
    }

    @Test
    public void testCreateEvent() {
        when(repository.save(any(EventEntity.class))).thenReturn(eventEntity);
        Event result = eventService.createEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void testUpdateEvent() {
        when(repository.save(any(EventEntity.class))).thenReturn(eventEntity);
        Event result = eventService.updateEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void testDeleteEvent() {

    }
}
