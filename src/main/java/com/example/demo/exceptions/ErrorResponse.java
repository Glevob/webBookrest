package com.example.demo.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {

    private final Long CodeID;
    private final String Message;
    private final String error;
    private final Integer httpCode;
    private final String details;
}
