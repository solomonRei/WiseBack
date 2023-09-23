package com.teamback.wise.services;

import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationService.class);
    private final RefreshTokenService refreshTokenService;
    private final JWTTokenService jwtTokenService;

    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationService(RefreshTokenService refreshTokenService,
                                       JWTTokenService jwtTokenService,
                                       UserRepository userRepository) {
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    public AuthResponse authenticate(GoogleUserResponse googleUserResponse) {
        logger.info("Authenticating user: " + googleUserResponse.getEmail());


        if (!userRepository.existsByEmail(googleUserResponse.getEmail())) {
            logger.info("User does not exist. Adding user to database.");
            //TODO: add user to database
        }

        String token = jwtTokenService.generateAccessToken(googleUserResponse.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(googleUserResponse.getUsername());

        return AuthResponse.builder()
                .email(googleUserResponse.getEmail())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }

        return null;
    }

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }

        return null;
    }
}
