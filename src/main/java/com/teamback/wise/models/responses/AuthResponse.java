package com.teamback.wise.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private final String email;
    private final String accessToken;
    private final String refreshToken;
}
