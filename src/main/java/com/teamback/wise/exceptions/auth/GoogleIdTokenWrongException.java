package com.teamback.wise.exceptions.auth;

public class GoogleIdTokenWrongException extends RuntimeException {

    public GoogleIdTokenWrongException(String message) {
        super(String.format("Google ID token is wrong: %s", message));
    }

}
