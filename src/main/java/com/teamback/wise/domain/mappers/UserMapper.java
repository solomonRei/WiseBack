package com.teamback.wise.domain.mappers;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.models.requests.UserCreateRequest;
import com.teamback.wise.models.responses.GoogleUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserCreateRequest mapGoogleUserResponseToUserCreateRequest(GoogleUserResponse googleUserResponse);

    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshTokens", ignore = true)
    @Mapping(target = "statistic", ignore = true)
    UserEntity mapUserCreateRequestToUserEntity(UserCreateRequest userCreateRequest);
}
