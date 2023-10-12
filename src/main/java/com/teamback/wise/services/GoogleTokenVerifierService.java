package com.teamback.wise.services;

import com.teamback.wise.models.responses.GoogleUserResponse;

public interface GoogleTokenVerifierService {

    GoogleUserResponse verifyGoogleId(String googleId);
}
