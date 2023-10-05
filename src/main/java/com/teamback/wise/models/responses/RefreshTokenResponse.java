package com.teamback.wise.models.responses;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class RefreshTokenResponse {
    private final String accessToken;

    private final String refreshToken;

    private final Instant expirationTime;
}
