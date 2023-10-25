package com.teamback.wise.services.youtube.api.Impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.gson.Gson;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.youtube.ApiKeyNotFoundException;
import com.teamback.wise.exceptions.youtube.FileYouTubeApiKeyErrorException;
import com.teamback.wise.exceptions.youtube.YoutubeAuthErrorException;
import com.teamback.wise.services.youtube.api.YouTubeApiKeyService;
import com.teamback.wise.utils.keys.KeyDataStructure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class YouTubeApiKeyServiceImpl implements YouTubeApiKeyService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    private final Gson gson;

    private final List<String> partsStatistics = Collections.singletonList("statistics");

    @Override
    public YouTube initService() {
        try {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            return new YouTube.Builder(httpTransport, new GsonFactory(), null)
                    .setApplicationName(googleConfigurationProperties.getGoogleApplicationName())
                    .setYouTubeRequestInitializer(new YouTubeRequestInitializer(getRandomKey()))
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
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

        } catch (IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }

    public List<String> readKeysFromFile() {
        var resource = new ClassPathResource(googleConfigurationProperties.getYouTubeKeysFilePath());

        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            var keyData = gson.fromJson(reader, KeyDataStructure.class);
            return keyData.getYouTubeApiKeys();
        } catch (IOException e) {
            log.error("Error during reading keys from file " + e.getMessage());
            throw new FileYouTubeApiKeyErrorException("Error during reading keys from file");
        }
    }

    private String getRandomKey() {
        Random random = new Random();
        var apiKeys = readKeysFromFile();
        var randomString = apiKeys.get(random.nextInt(apiKeys.size()));

        if (randomString == null) {
            throw new ApiKeyNotFoundException("No API keys found");
        }
        return randomString;
    }


}


