package com.epam.core.repository;

import com.epam.core.entity.EventEntity;
import com.epam.core.model.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageTest {

    private Storage storage = new Storage();
    private Entity entity;

    @Before
    public void setup() {
        entity = new EventEntity();
        entity.setId(123);
        storage.save("event", entity);
    }

    @Test
    public void testGet1() {
        assertEquals(entity, storage.get("event", 123));
    }

    @Test
    public void testGet2() {
        assertEquals(entity, storage.get("event:123"));
    }

    @Test
    public void testGetKeyArray() {
        Object[] expected = new Object[] {"event:123"};
        assertArrayEquals(expected, storage.getKeyArray());
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
