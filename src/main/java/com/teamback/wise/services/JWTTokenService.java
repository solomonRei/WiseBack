package com.teamback.wise.services;

import com.teamback.wise.models.responses.dto.TokenDto;

public interface JWTTokenService {

    TokenDto generateAccessToken(String userId);

}
