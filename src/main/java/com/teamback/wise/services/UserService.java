package com.teamback.wise.services;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.UserMapper;
import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.exceptions.user.UserNotFoundException;
import com.teamback.wise.models.requests.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserEntity createOrGetUser(UserCreateRequest userCreateRequest) {
        return userRepository.findByEmail(userCreateRequest.getEmail())
                .orElseGet(() -> {
                    log.info("User not found in the repository. Creating a new user.");
                    var newUser = UserMapper.INSTANCE.mapUserCreateRequestToUserEntity(userCreateRequest);

                    userRepository.save(newUser);
                    return newUser;
                });
    }
}
