package com.epam.core.dto;

import com.epam.core.model.Event;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EventDto implements Event {
    private long id;
    private String title;
    private Date date;
    private BigDecimal ticketPrice;
}
