package com.teamback.wise.services;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.RefreshTokenMapper;
import com.teamback.wise.domain.repositories.RefreshTokenRepository;
import com.teamback.wise.models.responses.dto.RefreshTokenDto;
import com.teamback.wise.security.JWTConfigProperties;
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
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }
}
