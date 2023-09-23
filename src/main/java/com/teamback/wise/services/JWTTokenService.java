package com.teamback.wise.services;

import com.teamback.wise.security.JWTConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JWTTokenService {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenService.class);

    private final JwtEncoder encoder;
    private final JWTConfigProperties jwtConfigProperties;

    @Autowired
    public JWTTokenService(JwtEncoder encoder,
                           JWTConfigProperties jwtConfigProperties) {
        this.encoder = encoder;
        this.jwtConfigProperties = jwtConfigProperties;
    }

    public String generateAccessToken(String userId) {
        logger.info("Generating access token for user: " + userId);
        Date expirationTime = Date.from(Instant.now().plusMillis(jwtConfigProperties.getExpirationTimeAccessTokenMs()));
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(jwtConfigProperties.getIssuer())
                .issuedAt(Instant.now())
                .expiresAt(expirationTime.toInstant())
                .subject(userId)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String generateRefreshToken(String userId) {
        logger.info("Generating refresh token for user: " + userId);
        Date expirationTime = Date.from(Instant.now().plusMillis(jwtConfigProperties.getExpirationTimeRefreshTokenMs()));
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(jwtConfigProperties.getIssuer())
                .issuedAt(Instant.now())
                .expiresAt(expirationTime.toInstant())
                .subject(userId)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
