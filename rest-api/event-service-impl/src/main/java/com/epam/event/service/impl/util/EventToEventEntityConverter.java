package com.epam.event.service.impl.util;

import com.epam.event.dao.model.EventEntity;
import com.epam.event.service.dto.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventToEventEntityConverter {

//    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static ObjectMapper mapper;

    @Autowired
    private ObjectMapper tmpMapper;

    @PostConstruct
    public void init() {
        mapper = tmpMapper;
    }

    public static Event convertEventEntityToEvent(EventEntity eventEntity) {
        return mapper.convertValue(eventEntity, Event.class);
    }

    public static EventEntity convertEventToEventEntity(Event event) {
        return mapper.convertValue(event, EventEntity.class);
    }
}
