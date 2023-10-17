package com.teamback.wise.exceptions.youtube;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class YoutubeAuthErrorException extends RuntimeException {

    public YoutubeAuthErrorException(String message) {
        super(message);
    }

}
