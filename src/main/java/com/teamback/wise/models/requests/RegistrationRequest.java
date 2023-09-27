package com.teamback.wise.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotBlank(message = "GoogleId cannot be empty.")
    @Size(min = 5, max = 320, message = "Size of the GoogleId should be from 5 to 320 characters.")
    private String googleIdToken;
}
