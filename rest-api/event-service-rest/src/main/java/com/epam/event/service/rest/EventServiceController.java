package com.epam.event.service.rest;

import com.epam.event.service.api.EventService;
import com.epam.event.service.dto.Event;
import com.epam.event.service.rest.model.EventResponse;
import com.epam.event.service.rest.model.EventResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventServiceController {

    private EventService service;
    private EventResponseAssembler assembler;

    public EventServiceController(EventService eventService, EventResponseAssembler assembler) {
        this.service = eventService;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all events")
    @ApiResponse(
            responseCode = "200",
            description = "Returned all events",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EventResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EventResponse>> getEvents(
            @Parameter(description = "title of events to be searched")
            @RequestParam Optional<String> title
    ) {
        List<Event> events = title.map(s -> service.getAllEventsByTitle(s)).orElseGet(() -> service.getAllEvents());
        return ResponseEntity.ok(assembler.toCollectionModel(events));
    }

    @Operation(summary = "Get event by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "id of event to be searched")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(assembler.toModel(service.getEvent(id)));
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
        ;
        return new ResponseEntity<>(assembler.toModel(service.createEvent(event)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "event to be updated")
            @RequestBody Event event,
            @Parameter(description = "id of event to be updated")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(assembler.toModel(service.updateEvent(event, id)));
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
        service.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
