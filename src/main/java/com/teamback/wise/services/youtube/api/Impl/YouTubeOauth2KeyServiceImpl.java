package com.teamback.wise.services.youtube.api.Impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.youtube.YoutubeAuthErrorException;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
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
        Credential credential = new GoogleCredential().setAccessToken(ACCESS_TOKEN);

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
            Channel userChannel = channels.get(0);
            return userChannel.getId();
        }

        return null;
    }


    @Override
    public ChannelListResponse getChannelStatistics(String channelId) {
        try {
            var youtubeService = initService();
            if (channelId == null) {
                channelId = getAuthenticatedUserChannelId(youtubeService);
            }
            String apiUrl = API_URL + "?ids=channel==" + channelId +
                    "&startDate=" + START_DATE +
                    "&endDate=" + END_DATE +
                    "&metrics=" + METRICS +
                    "&dimensions=" + DIMENSIONS +
                    "&sort=" + SORT;

            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(request -> {
                HttpHeaders headers = request.getHeaders();
                headers.setAuthorization("Bearer " + ACCESS_TOKEN);
            });

            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(apiUrl));
            HttpResponse response = request.execute();

            String responseBody = response.parseAsString();
            System.out.println("Response: " + responseBody);

            YouTube.Channels.List request2 = youtubeService
                    .channels()
                    .list(Collections.singletonList("statistics"))
                    .set("id", Collections.singletonList(channelId));
            return request2.execute();

        } catch (IOException | GeneralSecurityException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }
}
