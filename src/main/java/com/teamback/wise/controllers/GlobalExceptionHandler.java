package com.teamback.wise.controllers;

import com.teamback.wise.exceptions.auth.ExpiredRefreshTokenException;
import com.teamback.wise.exceptions.auth.FailedGoogleAuthException;
import com.teamback.wise.exceptions.auth.GoogleIdTokenWrongException;
import com.teamback.wise.exceptions.auth.InvalidRefreshTokenException;
import com.teamback.wise.models.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GoogleIdTokenWrongException.class})
    public ResponseEntity<ExceptionResponse> wrongGoogleIdTokenException(GoogleIdTokenWrongException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {FailedGoogleAuthException.class})
    public ResponseEntity<ExceptionResponse> failedGoogleAuthException(FailedGoogleAuthException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {ExpiredRefreshTokenException.class})
    public ResponseEntity<ExceptionResponse> expiredRefreshTokenException(ExpiredRefreshTokenException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {InvalidRefreshTokenException.class})
    public ResponseEntity<ExceptionResponse> invalidRefreshTokenException(InvalidRefreshTokenException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
