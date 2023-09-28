package com.teamback.wise.services;


import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.models.responses.GoogleUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleTokenVerifierService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    public GoogleUserResponse verifyGoogleId(String googleId) {
        log.info("Verifying Google ID: " + googleId);

        return GoogleUserResponse.builder()
                .username("seo")
                .email("test@mail.ru")
                .build();
    }
}
