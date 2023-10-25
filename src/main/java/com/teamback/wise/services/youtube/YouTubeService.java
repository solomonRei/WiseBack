package com.teamback.wise.services.youtube;

import com.teamback.wise.domain.entities.StatisticEntity;

public interface YouTubeService {

    boolean isChannelIdValid(String channelId);

    StatisticEntity getChannelStatistics(String channelId);

    StatisticEntity getChannelStatistics();

    StatisticEntity updateOrInsertChannelStatistics(String channelId);

    void updateOrInsertChannelCountryStatistics(String accessToken);
}
