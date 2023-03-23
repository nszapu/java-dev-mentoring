package com.epam.core.dto;

import com.epam.core.model.Ticket;
import lombok.Data;

@Data
public class TicketDto implements Ticket {
    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;
}

