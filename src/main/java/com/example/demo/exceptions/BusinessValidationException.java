package com.example.demo.exceptions;

public class BusinessValidationException extends RuntimeException {

    private final String details;

    public BusinessValidationException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
