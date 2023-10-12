package com.teamback.wise.services.Impl;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.mappers.UserMapper;
import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.models.requests.UserCreateRequest;
import com.teamback.wise.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
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
