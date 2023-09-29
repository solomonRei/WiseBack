package com.teamback.wise.models.responses.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class RefreshTokenDto {
    private Instant expirationDate;

    private String refreshToken;
}
