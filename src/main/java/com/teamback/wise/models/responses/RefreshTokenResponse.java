package com.teamback.wise.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenResponse {
    private final String accessToken;

    private final String refreshToken;

    private final Instant expirationTime;
}
