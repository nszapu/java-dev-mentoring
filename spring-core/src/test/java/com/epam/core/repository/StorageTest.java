package com.epam.core.repository;

import com.epam.core.entity.EventEntity;
import com.epam.core.model.Entity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StorageTest {

    private Storage storage = new Storage();
    private Map<String, Entity> repository;
    private Entity entity;

    @Before
    public void setup() {
        repository = new ConcurrentHashMap<>();
        entity = new EventEntity();
        entity.setId(123);
        storage.save("event", entity);
    }

    @Test
    public void testGet1() {
        repository.put("event:123", entity);
        assertEquals(repository.get("event:123"), storage.get("event", 123));
    }

    @Test
    public void testGet2() {
        repository.put("event:123", entity);
        assertEquals(repository.get("event:123"), storage.get("event:123"));
    }

    @Test
    public void testGetKeyArray() {
        Object[] expected = new Object[] {"event:123"};
        assertEquals(expected, storage.getKeyArray());
    }

    @Test
    public void testSave() {
        Entity event = new EventEntity();
        event.setId(1);
        Entity result = storage.save("event", event);
        assertEquals(2, storage.getKeyArray().length);
        assertEquals(event, result);
    }

    @Test
    public void testDelete() {
        boolean result = storage.delete("event", 123);
        assertTrue(result);
        assertEquals(0, storage.getKeyArray().length);
    }

    @Test
    public void testDeleteFail() {
        assertFalse(storage.delete("test", 1));
    }

    @Test
    public void testToString() {
        String expected = "event:123: EventEntity(id=123, title=null, date=null)\n";
        assertEquals(expected, storage.toString());
    }
}
