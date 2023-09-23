package com.teamback.wise.services;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.teamback.wise.models.responses.GoogleUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenVerifierService {

    @Value("${google.client-id}")
    private String ClientId;

    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenVerifierService.class);

    public GoogleUserResponse verifyGoogleId(String googleId) throws GeneralSecurityException, IOException {
        logger.info("Verifying Google ID: " + googleId);

        return GoogleUserResponse.builder()
                .username("seo")
                .email("test@mail.ru")
                .build();

//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//                .setAudience(Collections.singletonList(ClientId))
//                .build();
//
//        GoogleIdToken idToken = verifier.verify(googleId);
//        if (idToken != null) {
//            Payload payload = idToken.getPayload();
//            String userId = payload.getSubject();
//            String email = payload.getEmail();
//
//            logger.info("Writing Google Info: " + userId + " " + email);
//
//            return GoogleUserResponse.builder()
//                    .username(userId)
//                    .email(email)
//                    .build();
//
//        } else {
//            logger.error("Invalid ID token.");
//
//            //TODO: use GlobalExceptionHandler
//            throw new RuntimeException("Invalid ID token.");
//        }

    }
}
