package com.teamback.wise.services;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.UserMapper;
import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.models.requests.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            //TODO: add global exception handler
            log.error("User not found");
            throw new RuntimeException("User not found");
        }
    }

    public UserEntity createOrGetUser(UserCreateRequest userCreateRequest) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userCreateRequest.getEmail());
        if (userOptional.isPresent()) {
            log.info("User is already registered");
            return userOptional.get();
        } else {
            log.info("Creating User");
            var newUser = UserMapper.INSTANCE.mapUserCreateRequestToUserEntity(userCreateRequest);
            userRepository.save(newUser);
            return newUser;
        }
    }
}
