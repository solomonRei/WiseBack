package com.teamback.wise.domain.mappers;

import com.google.api.services.youtube.model.Channel;
import com.teamback.wise.domain.entities.UserProfileEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.google.api.client.util.DateTime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.teamback.wise.models.responses.youtube.ProfileByIdResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    @Mapping(target = "youtubeChannelId", source = "channel.id")
    @Mapping(target = "youtubeChannelName", source = "channel.snippet.title")
    @Mapping(target = "profilePictureUrl", source = "channel.snippet.thumbnails.default.url")
    @Mapping(target = "youtubeHandle", source = "channel.snippet.customUrl")
    @Mapping(target = "youtubeRegistrationDate", expression = "java(parseYoutubeDate(channel.getSnippet().getPublishedAt()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "youtubeFirstVideoDate", ignore = true)
    @Mapping(target = "user", source = "user")
    UserProfileEntity channelResponseToUserProfileEntity(Channel channel, UserEntity user);

    @Mapping(target = "youtubeChannelId", source = "userProfileEntity.youtubeChannelId")
    @Mapping(target = "youtubeChannelName", source = "userProfileEntity.youtubeChannelName")
    @Mapping(target = "profilePictureUrl", source = "userProfileEntity.profilePictureUrl")
    @Mapping(target = "youtubeHandle", source = "userProfileEntity.youtubeHandle")
    @Mapping(target = "youtubeRegistrationDate", expression = "java(userProfileEntity.getYoutubeRegistrationDate().toString())")
    @Mapping(target = "youtubeFirstVideoDate", ignore = true)
    ProfileByIdResponse userProfileEntityToProfileByIdResponse(UserProfileEntity userProfileEntity);


    default ZonedDateTime parseYoutubeDate(DateTime youtubeDate) {
        return ZonedDateTime.parse(youtubeDate.toStringRfc3339(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }


}
