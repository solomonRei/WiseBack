package com.teamback.wise.exceptions;

public class JWTInvalidException extends RuntimeException{
    public JWTInvalidException(String message) {
        super(message);
    }
}
