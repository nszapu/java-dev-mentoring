package com.epam.event.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long id;
    private String title;
    private String place;
    private String speaker;
    private String eventType;
    private LocalDate dateTime;
}
