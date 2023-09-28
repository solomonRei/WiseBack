package com.teamback.wise.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotBlank(message = "GoogleId cannot be empty.")
    private String googleIdToken;
}
