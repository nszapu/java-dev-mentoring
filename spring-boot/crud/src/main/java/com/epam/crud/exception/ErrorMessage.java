package com.epam.crud.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
