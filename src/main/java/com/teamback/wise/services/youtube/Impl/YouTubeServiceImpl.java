package com.teamback.wise.services.youtube.Impl;

import com.teamback.wise.domain.repositories.StatisticRepository;
import com.teamback.wise.services.youtube.YouTubeService;
import com.teamback.wise.services.youtube.api.YouTubeApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YouTubeServiceImpl implements YouTubeService {

    private final StatisticRepository statisticRepository;

    private final YouTubeApiKeyService youTubeApiKeyService;

    public void getStatistics(String channelId) {

        // TODO implement here
    }

    public void saveChannelStatistics(String channelId) {
        var data = youTubeApiKeyService.getChannelStatistics(channelId);
        if (data != null) {
            // TODO implement here
        }
    }
}
