package com.teamback.wise.services.youtube.api.Impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.teamback.wise.configurations.GoogleConfigurationProperties;
import com.teamback.wise.exceptions.youtube.AuthenticatedUserChannelIdNotFoundException;
import com.teamback.wise.exceptions.youtube.YoutubeAuthErrorException;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
import com.teamback.wise.utils.DateUtils;
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

    private final DateUtils dateUtils;

    private static final String START_DATE = "2014-01-01";

    private static final String METRICS = "views,estimatedMinutesWatched,averageViewDuration,averageViewPercentage,subscribersGained";

    private static final String DIMENSIONS = "day";

    private static final String SORT = "day";

    @Override
    public YouTube initService(String accessToken) {
        try {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null));
            HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credentials);

            return new YouTube.Builder(httpTransport, new GsonFactory(), adapter)
                    .setApplicationName(googleConfigurationProperties.getGoogleApplicationName())
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }

    private String getAuthenticatedUserChannelIdMethod(YouTube youtube) throws IOException {
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

    public String getAuthenticatedUserChannelId(String accessToken) {
        try {
            return getAuthenticatedUserChannelIdMethod(initService(accessToken));
        } catch (IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }

    @Override
    public ChannelListResponse getChannelStatistics(String accessToken) {
        try {
            var youtubeService = initService(accessToken);
            var channelId = getAuthenticatedUserChannelId(accessToken);
            if (channelId == null) {
                throw new AuthenticatedUserChannelIdNotFoundException("Authenticated user channel id not found");
            }

            var builder = UriComponentsBuilder.fromUriString(googleConfigurationProperties.getYouTubeApiUrl() + "reports")
                    .queryParam("ids", "channel==" + channelId)
                    .queryParam("startDate", START_DATE)
                    .queryParam("endDate", dateUtils.formatToRequiredDate(dateUtils.getCurrentDate()))
                    .queryParam("metrics", METRICS)
                    .queryParam("dimensions", DIMENSIONS)
                    .queryParam("sort", SORT);

            var apiUrl = builder.toUriString();
            var requestFactory = new NetHttpTransport().createRequestFactory(request -> {
                HttpHeaders headers = request.getHeaders();
                headers.setAuthorization("Bearer " + accessToken);
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

        } catch (IOException e) {
            log.error("Error during request to Youtube " + e.getMessage());
            throw new YoutubeAuthErrorException("Error in authorization in Youtube");
        }
    }
}
