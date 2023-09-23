package com.teamback.wise.services;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.repositories.RefreshTokenRepository;
import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.security.JWTConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JWTConfigProperties jwtConfigProperties;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserRepository userRepository,
                               JWTConfigProperties jwtConfigProperties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtConfigProperties = jwtConfigProperties;
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public String createRefreshToken(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            Optional<RefreshTokenEntity> existingRefreshTokenOptional = refreshTokenRepository.findByUser(user);

            if (existingRefreshTokenOptional.isPresent()) {
                RefreshTokenEntity existingRefreshToken = existingRefreshTokenOptional.get();
                existingRefreshToken.setExpiryDate(Instant.now().plusMillis(jwtConfigProperties.getExpirationTimeRefreshTokenMs()));
                existingRefreshToken.setCreatedAt(Instant.now());
                existingRefreshToken.setToken(UUID.randomUUID().toString());

                existingRefreshToken = refreshTokenRepository.save(existingRefreshToken);

                return existingRefreshToken.getToken();
            } else {
                RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                        .user(user)
                        .expiryDate(Instant.now().plusMillis(jwtConfigProperties.getExpirationTimeRefreshTokenMs()))
                        .createdAt(Instant.now())
                        .token(UUID.randomUUID().toString())
                        .build();

                refreshToken = refreshTokenRepository.save(refreshToken);

                return refreshToken.getToken();
            }
        } else {
            //TODO: add global exception handler
            logger.error("User not found");
            throw new RuntimeException("User not found");
        }
    }
}
