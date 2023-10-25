package com.teamback.wise.utils;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.exceptions.user.UserNotAuthenticatedException;
import com.teamback.wise.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserUtils {

    private final UserService userService;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }

        throw new UserNotAuthenticatedException("User is not authenticated.");
    }

    public UserEntity getCurrentUserEntity() {
        String username = getCurrentUsername();
        return userService.getUserById(username);
    }
}
