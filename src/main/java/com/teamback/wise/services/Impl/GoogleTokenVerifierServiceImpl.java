package com.teamback.wise.services.Impl;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.auth.FailedGoogleAuthException;
import com.teamback.wise.exceptions.auth.GoogleIdTokenWrongException;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.services.GoogleTokenVerifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleTokenVerifierServiceImpl implements GoogleTokenVerifierService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    public GoogleUserResponse verifyGoogleId(String accessToken) {
        try {
            log.info("Verifying Google Access Token: " + accessToken);
            var httpTransport = new NetHttpTransport();
            HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(
                    new GenericUrl(googleConfigurationProperties.getGoogleApiUrl() + "tokeninfo?access_token=" + accessToken));

            HttpResponse response = request.execute();

            if (response.getStatusCode() == 200) {
                log.info("Google Access Token is valid.");
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.parseAsString(), JsonObject.class);

                String userId = jsonResponse.get("sub").getAsString();
                String email = jsonResponse.get("email").getAsString();

                return GoogleUserResponse.builder()
                        .username(userId)
                        .email(email)
                        .build();
            } else {
                log.error("Invalid access token. Status not 200.");
                throw new GoogleIdTokenWrongException(accessToken);
            }
        } catch (IOException e) {
            log.error("Error authenticating user with Google ID.");
            throw new FailedGoogleAuthException("Server Google authentication error.");
        }
    }
}
