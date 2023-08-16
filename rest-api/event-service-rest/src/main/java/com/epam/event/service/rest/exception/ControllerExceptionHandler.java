package com.epam.event.service.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(NoSuchElementException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        message.setTimestamp(LocalDateTime.now());
        message.setMessage(exception.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
