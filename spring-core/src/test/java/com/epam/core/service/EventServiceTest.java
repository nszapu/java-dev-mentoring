package com.epam.core.service;

import com.epam.core.dao.EventDao;
import com.epam.core.entity.EventEntity;
import com.epam.core.model.Event;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventDao eventDao;
    @InjectMocks
    private EventService eventService;
    private Event event;
    private SimpleDateFormat dateFormat;
    private List<Event> events;

    @SneakyThrows
    @Before
    public void setup() {
        event = new EventEntity();
        event.setId(123);
        event.setTitle("test");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-01-01"));
        events = new ArrayList<>();
        events.add(event);
    }

    @Test
    public void testGetEventById() {
        when(eventDao.get(anyLong())).thenReturn(event);
        Event result = eventService.getEventById(anyLong());
        assertEquals("test", result.getTitle());
    }

    @Test
    public void testGetEventsByTitle() {
        when(eventDao.getEvents()).thenReturn(events);
        List<Event> result = eventService.getEventsByTitle("test", 1, 1);
        assertEquals(events.size(), result.size());
    }

    @SneakyThrows
    @Test
    public void testGetEventsByDay() {
        when(eventDao.getEvents()).thenReturn(events);
        List<Event> result = eventService.getEventsByDay(dateFormat.parse("2023-01-01"), 1, 1);
        assertEquals(events.size(), result.size());
    }

    @Test
    public void testCreateEvent() {
        when(eventDao.save(event)).thenReturn(event);
        Event result = eventService.createEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void testUpdateEvent() {
        when(eventDao.save(event)).thenReturn(event);
        Event result = eventService.updateEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void testDeleteEvent() {
        when(eventDao.delete(anyLong())).thenReturn(true);
        assertTrue(eventService.deleteEvent(anyLong()));
    }
}
