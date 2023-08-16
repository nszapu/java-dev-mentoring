package com.epam.event.service.rest.util;

import com.epam.event.service.dto.Event;
import com.epam.event.service.rest.model.EventResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventToEventResponseConverter {

    private static ObjectMapper mapper;
    @Autowired
    private ObjectMapper tmpMapper;

    @PostConstruct
    public void init() {
        mapper = tmpMapper;
    }

    public static EventResponse convertEventToEventResponse(Event event) {
        return mapper.convertValue(event, EventResponse.class);
    }

    public static Event convertEventResponseToEvent(EventResponse eventResponse) {
        return mapper.convertValue(eventResponse, Event.class);
    }
}
