package com.teamback.wise.controllers;

import com.teamback.wise.models.responses.AuthResponse;
import com.teamback.wise.models.responses.GoogleUserResponse;
import com.teamback.wise.services.CustomAuthenticationService;
import com.teamback.wise.services.GoogleTokenVerifierService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final CustomAuthenticationService customAuthenticationService;

    private final GoogleTokenVerifierService googleTokenVerifierService;

    public AuthenticationController(CustomAuthenticationService customAuthenticationService,
                                    GoogleTokenVerifierService googleTokenVerifierService) {
        this.customAuthenticationService = customAuthenticationService;
        this.googleTokenVerifierService = googleTokenVerifierService;
    }

    @PostMapping("/auth/google")
    public ResponseEntity<AuthResponse> authWithGoogleId(HttpServletRequest request) {
        try {
            logger.info("Authenticating user with Google ID.");
            GoogleUserResponse googleUserResponse = googleTokenVerifierService.verifyGoogleId((String) request.getAttribute("googleIdToken"));
            AuthResponse response = customAuthenticationService.authenticate(googleUserResponse);

            logger.info("User is successfully authenticated.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error authenticating user with Google ID.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/test")
    public void test() {
        System.out.println("test");
    }

}
