package com.teamback.wise.services;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.models.requests.UserCreateRequest;

public interface UserService {

    UserEntity createOrGetUser(UserCreateRequest userCreateRequest);

    UserEntity getUserById(String username);
}
