package com.epam;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String place;
    @Column
    private String speaker;
    @Column
    private String eventType;
    @Column
    private LocalDate dateTime;
}
