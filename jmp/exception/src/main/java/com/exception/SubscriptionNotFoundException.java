package com.exception;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
