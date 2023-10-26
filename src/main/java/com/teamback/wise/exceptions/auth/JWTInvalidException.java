package com.teamback.wise.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JWTInvalidException extends RuntimeException {

    public JWTInvalidException(String message) {
        super(message);
    }
}
