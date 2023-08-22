package com.epam.event.service.impl.util;

import com.epam.event.dao.model.EventEntity;
import com.epam.event.service.dto.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventToEventEntityConverter {

    @Autowired
    private ObjectMapper mapper;

    public Event convertEventEntityToEvent(EventEntity eventEntity) {
        return mapper.convertValue(eventEntity, Event.class);
    }

    public EventEntity convertEventToEventEntity(Event event) {
        return mapper.convertValue(event, EventEntity.class);
    }
}
