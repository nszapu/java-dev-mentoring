package com.exception;

public class SubscriptionNotFoundException extends Exception {

    public SubscriptionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
