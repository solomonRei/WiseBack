package com.teamback.wise.services;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.RefreshTokenMapper;
import com.teamback.wise.domain.repositories.RefreshTokenRepository;
import com.teamback.wise.exceptions.auth.ExpiredRefreshTokenException;
import com.teamback.wise.exceptions.auth.InvalidRefreshTokenException;
import com.teamback.wise.models.responses.RefreshTokenResponse;
import com.teamback.wise.models.responses.dto.RefreshTokenDto;
import com.teamback.wise.security.JWTConfigProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JWTConfigProperties jwtConfigProperties;

    private final JWTTokenService jwtTokenService;

    public RefreshTokenDto createRefreshToken(UserEntity user) {
        var refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .expiryDate(Instant.now().plusSeconds(jwtConfigProperties.getExpirationTimeRefreshTokenMn() * 60L))
                .createdAt(Instant.now())
                .token(UUID.randomUUID().toString())
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);

        return RefreshTokenMapper.INSTANCE.refreshTokenEntityToRefreshTokenDto(refreshToken);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    public RefreshTokenEntity getRefreshTokenFromString(String tokenString) {
        return refreshTokenRepository.findByToken(tokenString)
                .orElseThrow(InvalidRefreshTokenException::new);
    }

    @Transactional
    public RefreshTokenResponse refresh(String tokenString) {
        var verifiedToken = verifyExpiration(getRefreshTokenFromString(tokenString));
        refreshTokenRepository.deleteByToken(verifiedToken.getToken());

        var refreshTokenDto = createRefreshToken(verifiedToken.getUser());
        var accessTokenDto = jwtTokenService.generateAccessToken(verifiedToken.getUser().getId().toString());

        return RefreshTokenResponse.builder()
                .refreshToken(refreshTokenDto.getRefreshToken())
                .expirationTime(accessTokenDto.getExpirationTime())
                .accessToken(accessTokenDto.getToken())
                .build();
    }

}
