package com.epam.event.service.rest.model;

import com.epam.event.service.dto.Event;
import com.epam.event.service.rest.EventServiceController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventResponseAssembler implements RepresentationModelAssembler<Event, EventResponse> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public EventResponse toModel(Event entity) {
        EventResponse eventResponse = mapper.convertValue(entity, EventResponse.class);
        eventResponse.add(linkTo(methodOn(EventServiceController.class).getEventById(entity.getId())).withSelfRel());
        eventResponse.add(linkTo(methodOn(EventServiceController.class).getEvents(Optional.empty())).withRel("events"));
        eventResponse.add(linkTo(methodOn(EventServiceController.class).getEvents(Optional.of(entity.getTitle()))).withRel("eventsWithTitle"));
        eventResponse.add(linkTo(methodOn(EventServiceController.class).createEvent(entity)).withRel("createEvent"));
        eventResponse.add(linkTo(methodOn(EventServiceController.class).updateEvent(entity, entity.getId())).withRel("updateEvent"));
        eventResponse.add(linkTo(methodOn(EventServiceController.class).deleteEvent(entity.getId())).withRel("deleteEvent"));
        return eventResponse;
    }

    @Override
    public CollectionModel<EventResponse> toCollectionModel(Iterable<? extends Event> entities) {
        CollectionModel<EventResponse> eventResponses = RepresentationModelAssembler.super.toCollectionModel(entities);
        eventResponses.add(linkTo(methodOn(EventServiceController.class).getEvents(Optional.empty())).withRel("events"));
        return eventResponses;
    }
}
