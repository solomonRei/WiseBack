package com.teamback.wise.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "refreshToken cannot be empty.")
    private String refreshToken;
}
