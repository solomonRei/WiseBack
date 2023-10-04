package com.teamback.wise.exceptions.auth;

public class InvalidRefreshTokenException extends RuntimeException {

        public InvalidRefreshTokenException(String refreshToken) {
            super(String.format("Invalid refresh token: %s", refreshToken));
        }

}
