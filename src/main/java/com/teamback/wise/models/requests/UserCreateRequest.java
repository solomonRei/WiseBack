package com.teamback.wise.models.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateRequest {

    private String username;

    private String email;
}
