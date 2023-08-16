package com.epam.event.service.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse extends RepresentationModel<EventResponse> {

    private long id;
    private String title;
    private String place;
    private String speaker;
    private String eventType;
    private LocalDate dateTime;
}
