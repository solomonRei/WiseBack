package com.teamback.wise.services.youtube.api;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface YouTubeKeyService {

    YouTube initService() throws GeneralSecurityException, IOException;

    ChannelListResponse getChannelStatistics(String channelId);

}
