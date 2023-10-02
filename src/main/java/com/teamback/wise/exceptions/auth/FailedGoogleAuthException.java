package com.teamback.wise.exceptions.auth;

public class FailedGoogleAuthException extends RuntimeException {

        public FailedGoogleAuthException(String message) {
            super(String.format("Failed to authenticate with Google: %s", message));
        }

}
