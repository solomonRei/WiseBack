package com.teamback.wise.services.Impl;

import com.teamback.wise.domain.mappers.UserMapper;
import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.services.JWTTokenService;
import com.teamback.wise.services.RefreshTokenService;
import com.teamback.wise.services.UserAuthenticationService;
import com.teamback.wise.services.UserService;
import com.teamback.wise.services.youtube.YouTubeService;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final JWTTokenService jwtTokenService;

    private final YouTubeOauth2KeyService youTubeOauth2KeyService;

    private final YouTubeService youTubeService;

    @Override
    public AuthResponse registration(GoogleUserResponse googleUserResponse, String googleIdToken) {
        log.info("Registration of the user: " + googleUserResponse.getEmail());

        var mappedUser = UserMapper.INSTANCE.mapGoogleUserResponseToUserCreateRequest(googleUserResponse);
        var user = userService.createOrGetUser(mappedUser);
        user.setYoutubeChannelId(youTubeOauth2KeyService.getAuthenticatedUserChannelId(googleIdToken));
        var tokenDto = jwtTokenService.generateAccessToken(String.valueOf(user.getId()));
        var refreshToken = refreshTokenService.createRefreshToken(user);

        youTubeService.updateOrInsertChannelStatistics(user);

        log.info("Successfully granted token to: " + user.getUsername());

        return AuthResponse.builder()
                .accessToken(tokenDto.getToken())
                .refreshToken(refreshToken.getRefreshToken())
                .expirationTime(tokenDto.getExpirationTime())
                .build();
    }

}
