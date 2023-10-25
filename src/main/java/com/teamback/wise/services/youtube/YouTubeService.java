package com.teamback.wise.services.youtube;

import com.teamback.wise.domain.entities.StatisticEntity;
import com.teamback.wise.domain.entities.UserProfileEntity;

public interface YouTubeService {

    boolean isChannelIdValid(String channelId);

    StatisticEntity getChannelStatistics(String channelId);

    StatisticEntity updateOrInsertChannelStatistics(String channelId);

    UserProfileEntity getUserProfile(String channelId);

    UserProfileEntity updateOrInsertUserProfile(String channelId);

    void updateOrInsertChannelCountryStatistics(String accessToken);
}
