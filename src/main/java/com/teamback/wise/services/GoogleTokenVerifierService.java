package com.teamback.wise.services;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.auth.FailedGoogleAuthException;
import com.teamback.wise.exceptions.auth.GoogleIdTokenWrongException;
import com.teamback.wise.models.responses.GoogleUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleTokenVerifierService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    public GoogleUserResponse verifyGoogleId(String googleId) {
        log.info("Verifying Google ID: " + googleId);

        try {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleConfigurationProperties.getClientId()))
                    .build();

            GoogleIdToken idToken = verifier.verify(googleId);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();

                log.info("Writing Google Info: " + userId + " " + email);

                return GoogleUserResponse.builder()
                        .username(userId)
                        .email(email)
                        .build();

            } else {
                log.error("Invalid ID token.");

                //TODO: use GlobalExceptionHandler
                throw new GoogleIdTokenWrongException(googleId);
            }

        } catch (Exception e) {
            log.error("Error authenticating user with Google ID.");
            throw new FailedGoogleAuthException(e.getMessage());
        }
    }
}
