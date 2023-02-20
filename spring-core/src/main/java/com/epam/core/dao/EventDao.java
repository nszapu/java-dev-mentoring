package com.epam.core.dao;

import com.epam.core.Application;
import com.epam.core.entity.EventEntity;
import com.epam.core.model.Event;
import com.epam.core.repository.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventDao {

    @Getter
    @Setter
    private String eventsPath;
    private List<Event> events = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    private ClassLoader classLoader = Application.class.getClassLoader();
    @Getter
    @Setter
    private Storage repository;

    public void loadEventsFromFile() throws IOException {
        events = Arrays.asList(mapper.readValue(new File(classLoader.getResource(eventsPath).getFile()), EventEntity[].class));
        events.forEach(event -> repository.save("event", event));
    }

    public Event get(long id) {
        return (Event) repository.get("event", id);
    }

    public List<Event> getEvents() {
        List<Event> result = new ArrayList<>();
        for (Object key: repository.getKeyArray()){
            if (key.toString().startsWith("event")){
                result.add((Event) repository.get(key.toString()));
            }
        }
        return result;
    }

    public Event save(Event event) {
        return (Event) repository.save("event", event);
    }

    public boolean delete(long id) {
        return repository.delete("event", id);
    }
}
