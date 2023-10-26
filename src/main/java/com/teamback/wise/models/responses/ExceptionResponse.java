package com.teamback.wise.models.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {

    private final LocalDateTime timestamp;

    private final int status;

    private final String error;

    private final String message;

}
