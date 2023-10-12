package com.teamback.wise.services.youtube.api.Impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.youtube.YoutubeAuthErrorException;
import com.teamback.wise.services.youtube.api.YouTubeApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class YouTubeApiKeyServiceImpl implements YouTubeApiKeyService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    private final List<String> partsStatistics = Collections.singletonList("statistics");

    @Override
    public YouTube initService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new YouTube.Builder(httpTransport, new GsonFactory(), null)
                .setApplicationName(googleConfigurationProperties.getGoogleApplicationName())
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(googleConfigurationProperties.getRandomKey()))
                .build();
    }

    @Override
    public ChannelListResponse getChannelStatistics(String channelId) {
        try {
            var youtubeService = initService();
            var request = youtubeService.channels()
                    .list(partsStatistics)
                    .setId(
                            Collections.singletonList(channelId)
                    );

            log.info("Request to Youtube with parts: " + partsStatistics + " and id: " + channelId);

            var response = request.execute();

            log.info("Response from Youtube: " + response);

            return response;

        } catch (GeneralSecurityException | IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }


}


