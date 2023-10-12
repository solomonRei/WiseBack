package com.teamback.wise.services;

import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;

public interface UserAuthenticationService {

    AuthResponse registration(GoogleUserResponse googleUserResponse);
}
