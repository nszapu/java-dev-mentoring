package com.epam.core.dao;

import com.epam.core.entity.EventEntity;
import com.epam.core.model.Event;
import com.epam.core.repository.Storage;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoTest {

    @Mock
    private Storage repository;
    @InjectMocks
    private EventDao eventDao;
    private Event event;
    private List<Event> events;

    @SneakyThrows
    @Before
    public void setup() {
        event = new EventEntity();
        event.setId(123);
        event.setTitle("test");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(dateFormat.parse("2023-01-01"));
        events = new ArrayList<>();
        events.add(event);
    }

//    @Test
//    public void testLoadEventsFromFile() {
//        when(repository.save("event", event)).thenReturn(event);
//        eventDao.loadEventsFromFile();
//        verify(eventDao).loadEventsFromFile();
//    }

    @Test
    public void testGet() {
        when(repository.get("event", 1)).thenReturn(event);
        assertEquals(event, eventDao.get(1));
    }

    @Test
    public void testGetEvents() {
        when(repository.getKeyArray()).thenReturn(new Object[] {"event:123"});
        when(repository.get("event:123")).thenReturn(event);
        assertEquals(events.size(), eventDao.getEvents().size());
    }

    @Test
    public void testSave() {
        when(repository.save(anyString(), any(Event.class))).thenReturn(event);
        assertEquals(event, eventDao.save(event));
    }

    @Test
    public void testDelete() {
        when(repository.delete(anyString(), anyLong())).thenReturn(true);
        assertTrue(eventDao.delete(1));
    }
}
