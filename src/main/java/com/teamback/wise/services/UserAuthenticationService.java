package com.teamback.wise.services;

import com.teamback.wise.domain.mappers.UserMapper;
import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.models.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final JWTTokenService jwtTokenService;

    public AuthResponse registration(GoogleUserResponse googleUserResponse) {
        log.info("Registration of the user: " + googleUserResponse.getEmail());

        var mappedUser = UserMapper.INSTANCE.mapGoogleUserResponseToUserCreateRequest(googleUserResponse);
        var user = userService.createOrGetUser(mappedUser);
        var tokenDto = jwtTokenService.generateAccessToken(String.valueOf(user.getId()));
        var refreshToken = refreshTokenService.createRefreshToken(user);

        log.info("Successfully granted token to: " + user.getUsername());

        return AuthResponse.builder()
                .accessToken(tokenDto.getToken())
                .refreshToken(refreshToken.getRefreshToken())
                .expirationTime(tokenDto.getExpirationTime())
                .build();
    }


}
