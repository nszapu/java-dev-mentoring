package com.epam.event.service.rest;

import com.epam.event.service.api.EventService;
import com.epam.event.service.dto.Event;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventServiceController {

    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getEvents() {
        return service.getAllEvents();
    }
}
