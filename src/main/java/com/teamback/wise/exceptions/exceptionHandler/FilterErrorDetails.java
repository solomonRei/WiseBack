package com.teamback.wise.exceptions.exceptionHandler;

import lombok.Getter;

@Getter
public class FilterErrorDetails {

    private final String timestamp;

    private final String message;

    public FilterErrorDetails(String timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}