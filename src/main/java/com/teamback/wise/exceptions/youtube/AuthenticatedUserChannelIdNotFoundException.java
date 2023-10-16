package com.teamback.wise.exceptions.youtube;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthenticatedUserChannelIdNotFoundException extends RuntimeException {

    public AuthenticatedUserChannelIdNotFoundException(String message) {
        super(message);
    }

}
