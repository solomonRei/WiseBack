package com.teamback.wise.exceptions.auth;

public class ExpiredRefreshTokenException extends RuntimeException {

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

}
