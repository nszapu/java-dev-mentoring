package com.epam.core.entity;

import com.epam.core.model.Event;
import lombok.Data;

import java.util.Date;

@Data
public class EventEntity implements Event {
    private long id;
    private String title;
    private Date date;
}
