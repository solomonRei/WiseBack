package com.teamback.wise.services.youtube.api.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.youtube.AuthenticatedUserChannelIdNotFoundException;
import com.teamback.wise.exceptions.youtube.YoutubeAuthErrorException;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class YouTubeOauth2KeyServiceImpl implements YouTubeOauth2KeyService {

    private final GoogleConfigurationProperties googleConfigurationProperties;

    private static final String API_URL = "https://youtubeanalytics.googleapis.com/v2/reports";

    private static final String START_DATE = "2014-01-01";

    private static final String END_DATE = "2023-12-31";

    private static final String METRICS = "views,estimatedMinutesWatched,averageViewDuration,averageViewPercentage,subscribersGained";

    private static final String DIMENSIONS = "day";

    private static final String SORT = "day";

    private static final String ACCESS_TOKEN = "";

    @Override
    public YouTube initService() throws GeneralSecurityException, IOException {

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var credential = new GoogleCredential().setAccessToken(ACCESS_TOKEN);

        return new YouTube.Builder(httpTransport, new GsonFactory(), credential)
                .setApplicationName(googleConfigurationProperties.getGoogleApplicationName())
                .build();
    }

    public String getAuthenticatedUserChannelId(YouTube youtube) throws IOException {
        YouTube.Channels.List channelsListRequest = youtube
                .channels()
                .list(Collections.singletonList("id"))
                .setMine(true);

        var response = channelsListRequest.execute();
        List<Channel> channels = response.getItems();

        if (channels != null && !channels.isEmpty()) {
            var userChannel = channels.get(0);
            return userChannel.getId();
        }

        throw new AuthenticatedUserChannelIdNotFoundException("Authenticated user channel id not found");
    }


    @Override
    public ChannelListResponse getChannelStatistics(String channelId) {
        try {
            var youtubeService = initService();
            if (channelId == null) {
                channelId = getAuthenticatedUserChannelId(youtubeService);
            }

            var builder = UriComponentsBuilder.fromUriString(API_URL)
                    .queryParam("ids", "channel==" + channelId)
                    .queryParam("startDate", START_DATE)
                    .queryParam("endDate", END_DATE)
                    .queryParam("metrics", METRICS)
                    .queryParam("dimensions", DIMENSIONS)
                    .queryParam("sort", SORT);

            var apiUrl = builder.toUriString();
            var requestFactory = new NetHttpTransport().createRequestFactory(request -> {
                HttpHeaders headers = request.getHeaders();
                headers.setAuthorization("Bearer " + ACCESS_TOKEN);
            });

            var request = requestFactory.buildGetRequest(new GenericUrl(apiUrl));
            var response = request.execute();

            var responseBody = response.parseAsString();
            System.out.println("Response: " + responseBody);

            var requestAuthUserStatistics = youtubeService
                    .channels()
                    .list(Collections.singletonList("statistics"))
                    .set("id", Collections.singletonList(channelId));
            return requestAuthUserStatistics.execute();

        } catch (IOException | GeneralSecurityException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }
}
