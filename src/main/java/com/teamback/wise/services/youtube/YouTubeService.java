package com.teamback.wise.services.youtube;

import com.teamback.wise.domain.entities.StatisticEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.entities.UserProfileEntity;

public interface YouTubeService {

    boolean isChannelIdValid(String channelId);

    StatisticEntity getChannelStatistics(String channelId);

    StatisticEntity getChannelStatistics();

    StatisticEntity updateOrInsertChannelStatistics(String channelId);

    void updateOrInsertChannelStatistics(UserEntity user);

    UserProfileEntity getUserProfile(String channelId);

    UserProfileEntity getCurrentUserProfile();

    UserProfileEntity updateOrInsertUserProfile(String channelId);

    void updateOrInsertChannelCountryStatistics(String accessToken);
}
