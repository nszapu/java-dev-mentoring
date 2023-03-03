package com.epam.core.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private EventEntity event;
    @ManyToOne
    private UserEntity user;
    @Column
    private String category;
    @Column
    private int place;
}

