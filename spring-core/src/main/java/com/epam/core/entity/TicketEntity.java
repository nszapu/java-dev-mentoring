package com.epam.core.entity;

import com.epam.core.model.Ticket;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class TicketEntity implements Ticket {
    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;
}

