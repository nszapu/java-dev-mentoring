package com.epam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventServiceController {

    @Autowired
    private EventService service;

    @GetMapping
    public List<Event> getEvents() {
        return service.getAllEvents();
    }
}
