package com.teamback.wise.services.youtube.api;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;

public interface YouTubeOauth2KeyService extends YouTubeKeyService {

    YouTube initService(String accessToken);

    ChannelListResponse getChannelStatistics(String accessToken);

    String getAuthenticatedUserChannelId(String accessToken);
}
