package com.epam.crud.exception;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(NoSuchElementException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        message.setTimestamp(LocalDateTime.now());
        message.setMessage(exception.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorMessage> entityExistsException(EntityExistsException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setStatusCode(HttpStatus.BAD_REQUEST.value());
        message.setTimestamp(LocalDateTime.now());
        message.setMessage(exception.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
