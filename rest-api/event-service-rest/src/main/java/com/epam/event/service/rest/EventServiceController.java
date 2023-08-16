package com.epam.event.service.rest;

import com.epam.event.service.api.EventService;
import com.epam.event.service.dto.Event;
import com.epam.event.service.rest.model.EventResponse;
import com.epam.event.service.rest.util.EventToEventResponseConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.event.service.rest.util.EventToEventResponseConverter.convertEventToEventResponse;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/events")
public class EventServiceController {

    private static final String EVENTS = "events";
    private EventService service;

    public EventServiceController(EventService eventService) {
        this.service = eventService;
    }

    @Operation(summary = "Get all events")
    @ApiResponse(
            responseCode = "200",
            description = "Returned all events",
            content = @Content(mediaType = "application/json")
    )
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents() {
        List<EventResponse> eventsResponse = service.getAllEvents().stream()
                .map(EventToEventResponseConverter::convertEventToEventResponse)
                .map(event -> event.add(linkTo(methodOn(this.getClass()).getEventById(event.getId())).withSelfRel()))
                .toList();
        return ResponseEntity.ok(eventsResponse);
    }

    @Operation(summary = "Get all events by title")
    @ApiResponse(
            responseCode = "200",
            description = "Returned all events with specific title",
            content = @Content(mediaType = "application/json")
    )
    @GetMapping("/{title}")
    public ResponseEntity<List<EventResponse>> getEventsByTitle(
            @Parameter(description = "title of the events to be searched")
            @PathVariable String title
    ) {
        List<EventResponse> eventResponses = service.getAllEventsByTitle(title).stream()
                .map(EventToEventResponseConverter::convertEventToEventResponse)
                .map(event -> event.add(linkTo(methodOn(this.getClass()).getEventById(event.getId())).withSelfRel()))
                .toList();
        return ResponseEntity.ok(eventResponses);
    }

    @Operation(summary = "Get event by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = EventResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @GetMapping("/event/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "id of event to be searched")
            @PathVariable long id
    ) {
        EventResponse eventResponse = convertEventToEventResponse(service.getEvent(id))
                .add(linkTo(methodOn(this.getClass()).getEvents()).withRel(EVENTS));
        return ResponseEntity.ok(eventResponse);
    }

    @Operation(summary = "Create event")
    @ApiResponse(
            responseCode = "201",
            description = "Created event",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
    )
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @Parameter(description = "event to be created")
            @RequestBody Event event
    ) {
        EventResponse eventResponse = convertEventToEventResponse(service.createEvent(event))
                .add(linkTo(methodOn(this.getClass()).getEvents()).withRel(EVENTS));
        return new ResponseEntity<>(eventResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "event to be updated")
            @RequestBody Event event
    ) {
        service.getEvent(event.getId());
        EventResponse eventResponse = convertEventToEventResponse(service.updateEvent(event))
                .add(linkTo(methodOn(this.getClass()).getEvents()).withRel(EVENTS));
        return ResponseEntity.ok(eventResponse);
    }

    @Operation(summary = "Delete event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @Parameter(description = "id of event to be deleted")
            @PathVariable long id
    ) {
        service.getEvent(id);
        service.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
