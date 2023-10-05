package com.teamback.wise.exceptions.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid refresh token.")
public class InvalidRefreshTokenException extends RuntimeException {

        public InvalidRefreshTokenException() {
            super("Invalid refresh token.");
        }

}
