package com.teamback.wise.services.Impl;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.auth.FailedGoogleAuthException;
import com.teamback.wise.exceptions.auth.GoogleIdTokenWrongException;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.services.GoogleTokenVerifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleTokenVerifierServiceImpl implements GoogleTokenVerifierService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    public GoogleUserResponse verifyGoogleId(String googleId) {
        log.info("Verifying Google ID: " + googleId);

        try {

            var verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleConfigurationProperties.getClientId()))
                    .build();

            var idToken = verifier.verify(googleId);
            if (idToken != null) {
                var payload = idToken.getPayload();
                var userId = payload.getSubject();
                var email = payload.getEmail();

                log.info("User is authenticated");

                return GoogleUserResponse.builder()
                        .username(userId)
                        .email(email)
                        .build();

            } else {
                log.error("Invalid ID token.");
                throw new GoogleIdTokenWrongException(googleId);
            }

        } catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
            log.error("Error authenticating user with Google ID.");
            throw new FailedGoogleAuthException(e.getMessage());
        }
    }
}
