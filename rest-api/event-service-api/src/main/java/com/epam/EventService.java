package com.epam;

import java.util.List;

public interface EventService {

    Event createEvent(Event event);
    Event updateEvent(Event event);
    Event getEvent(Long id);
    boolean deleteEvent(Long id);
    List<Event> getAllEvents();
    List<Event> getAllEventsByTitle(String title);
}
