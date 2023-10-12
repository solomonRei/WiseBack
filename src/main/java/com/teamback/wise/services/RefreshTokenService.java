package com.teamback.wise.services;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.models.responses.RefreshTokenResponse;
import com.teamback.wise.models.responses.dto.RefreshTokenDto;

public interface RefreshTokenService {

    RefreshTokenDto createRefreshToken(UserEntity user);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

    RefreshTokenEntity getRefreshTokenFromString(String tokenString);

    RefreshTokenResponse refresh(String tokenString);

}
