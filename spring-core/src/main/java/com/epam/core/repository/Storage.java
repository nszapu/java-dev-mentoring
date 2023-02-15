package com.epam.core.repository;

import com.epam.core.model.Entity;
import com.epam.core.model.Event;
import com.epam.core.model.Ticket;
import com.epam.core.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {

    private Map<String, Entity> repository = new ConcurrentHashMap<>();

    public Entity get(String entityType, long id) {
        return repository.get(entityType + ":" + id);
    }

    public List<Event> getEvents() {
        List<Event> result = new ArrayList<>();
        for (Object key: repository.keySet().toArray()){
            if (key.toString().startsWith("event")){
                result.add((Event) repository.get(key));
            }
        }
        return result;
    }

    public List<Ticket> getTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Object key: repository.keySet().toArray()){
            if (key.toString().startsWith("ticket")){
                result.add((Ticket) repository.get(key));
            }
        }
        return result;
    }

    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        for (Object key: repository.keySet().toArray()){
            if (key.toString().startsWith("user")){
                result.add((User) repository.get(key));
            }
        }
        return result;
    }

    public void save(String entityType, Entity entity) {
        repository.put(entityType + ":" + entity.getId(), entity);
    }

    public void update(Entity entity) {

    }

    public boolean delete(String entityType, long id) {
        try {
            repository.remove(entityType + id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String toString() {
        StringBuilder content = new StringBuilder();
        repository.forEach((k, v) -> content.append(k).append(": ").append(v.toString()).append("\n"));
        return content.toString();
    }
}
