package com.teamback.wise.services;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.teamback.wise.models.responses.GoogleUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
public class GoogleTokenVerifierService {

    @Value("${google.client-id}")
    private String ClientId;

    @Value("${google.client-secret}")
    private String ClientToken;

    public GoogleUserResponse verifyGoogleId(String googleId) {
        log.info("Verifying Google ID: " + googleId);
        try {

        return GoogleUserResponse.builder()
                .username("seo")
                .email("test@mail.ru")
                .build();

        } catch (Exception e) {
            log.error("Error authenticating user with Google ID.");
            return null;
        }
    }
}
