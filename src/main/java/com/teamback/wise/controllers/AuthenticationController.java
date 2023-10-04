package com.teamback.wise.controllers;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.RefreshTokenMapper;
import com.teamback.wise.models.requests.RefreshTokenRequest;
import com.teamback.wise.models.requests.RegistrationRequest;
import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.models.responses.RefreshTokenResponse;
import com.teamback.wise.models.responses.dto.RefreshTokenDto;
import com.teamback.wise.services.GoogleTokenVerifierService;
import com.teamback.wise.services.RefreshTokenService;
import com.teamback.wise.services.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registration(@Valid @RequestBody RegistrationRequest RegistrationRequest) {
        log.info("Registering user with Google ID.");
        GoogleUserResponse googleUserResponse = googleTokenVerifierService.verifyGoogleId(RegistrationRequest.getGoogleIdToken());
        AuthResponse response = userAuthenticationService.registration(googleUserResponse);

        log.info("User is successfully registered.");
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refresh request received.");

        RefreshTokenResponse response = refreshTokenService.refresh(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(200).body(response);
    }

}
