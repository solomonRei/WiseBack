package com.teamback.wise.domain.mappers;

import com.google.api.services.youtube.model.Channel;
import com.teamback.wise.domain.entities.StatisticEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.models.responses.youtube.StatisticsByIdResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface StatisticMapper {

    StatisticMapper INSTANCE = Mappers.getMapper(StatisticMapper.class);

    @Mapping(target = "youtubeChannelId", source = "channel.id")
    @Mapping(target = "viewCount", source = "channel.statistics.viewCount")
    @Mapping(target = "videoCount", source = "channel.statistics.videoCount")
    @Mapping(target = "subscriberCount", source = "channel.statistics.subscriberCount")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "statisticsCountry", ignore = true)
    @Mapping(target = "user", source = "user")
    StatisticEntity statisticResponseToStatisticEntity(Channel channel, UserEntity user);

    @Mapping(target = "channelId", source = "statisticEntity.youtubeChannelId")
    StatisticsByIdResponse statisticEntityToStatisticResponse(StatisticEntity statisticEntity);

}
