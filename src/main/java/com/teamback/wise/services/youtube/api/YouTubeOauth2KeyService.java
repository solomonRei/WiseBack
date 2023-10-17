package com.teamback.wise.services.youtube.api;

import com.google.api.services.youtube.YouTube;

import java.io.IOException;

public interface YouTubeOauth2KeyService extends YouTubeKeyService {

    String getAuthenticatedUserChannelId(YouTube youtube) throws IOException;
}
