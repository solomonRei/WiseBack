package com.teamback.wise.services.Impl;

import com.teamback.wise.models.responses.dto.TokenDto;
import com.teamback.wise.security.JWTConfigProperties;
import com.teamback.wise.services.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JWTTokenServiceImpl implements JWTTokenService {

    private final JwtEncoder encoder;

    private final JWTConfigProperties jwtConfigProperties;

    public TokenDto generateAccessToken(String userId) {
        log.info("Generating access token for user: " + userId);

        var expirationTime = Date.from(Instant.now().plusSeconds(jwtConfigProperties.getExpirationTimeAccessTokenMn() * 60L));
        var claimsSet = JwtClaimsSet.builder()
                .issuer(jwtConfigProperties.getIssuer())
                .issuedAt(Instant.now())
                .expiresAt(expirationTime.toInstant())
                .subject(userId)
                .build();

        return TokenDto.builder()
                .token(encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue())
                .expirationTime(expirationTime.toInstant())
                .build();
    }
}
