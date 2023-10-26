package com.teamback.wise.services.youtube.api;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;

public interface YouTubeApiKeyService extends YouTubeKeyService {

    YouTube initService();

    ChannelListResponse getChannelStatistics(String channelId);

    ChannelListResponse getChannelProfile(String channelId);

}
