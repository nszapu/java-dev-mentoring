package com.epam.core.repository;

import com.epam.core.model.Entity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Storage {

    private Map<String, Entity> repository = new ConcurrentHashMap<>();

    public Entity get(String entityType, long id) {
        return repository.get(entityType + ":" + id);
    }
    public Entity get(String key) {
        return repository.get(key);
    }

    public Object[] getKeyArray() {
        return repository.keySet().toArray();
    }

    public void save(String entityType, Entity entity) {
        repository.put(entityType + ":" + entity.getId(), entity);
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
