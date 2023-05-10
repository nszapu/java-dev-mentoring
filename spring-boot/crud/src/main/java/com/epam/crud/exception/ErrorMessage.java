package com.epam.crud.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
