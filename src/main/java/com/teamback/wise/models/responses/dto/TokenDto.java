package com.teamback.wise.models.responses.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
public class TokenDto {

    private String token;

    private Instant expirationTime;

}
