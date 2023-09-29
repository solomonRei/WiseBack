package com.teamback.wise.domain.mappers;

import com.teamback.wise.domain.entities.RefreshTokenEntity;
import com.teamback.wise.models.responses.dto.RefreshTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface RefreshTokenMapper {

    RefreshTokenMapper INSTANCE = Mappers.getMapper(RefreshTokenMapper.class);

    @Mapping(target = "refreshToken", source = "token")
    @Mapping(target = "expirationDate", source = "expiryDate")
    RefreshTokenDto refreshTokenEntityToRefreshTokenDto(RefreshTokenEntity refreshTokenEntity);
}
