package com.teamback.wise.controllers;

import com.teamback.wise.domain.mappers.StatisticMapper;
import com.teamback.wise.domain.mappers.UserProfileMapper;
import com.teamback.wise.exceptions.youtube.ChannelIdNotValidException;
import com.teamback.wise.models.responses.youtube.ProfileByIdResponse;
import com.teamback.wise.models.responses.youtube.StatisticsByIdResponse;
import com.teamback.wise.services.youtube.YouTubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/youtube")
public class YoutubeController {

    private final YouTubeService youtubeService;

    @CrossOrigin(origins = "https://neptun.md")
    @GetMapping("/statistics/{channelId}")
    public ResponseEntity<StatisticsByIdResponse> getChannelStatisticsById(@PathVariable String channelId) {
        if (!youtubeService.isChannelIdValid(channelId)) {
            throw new ChannelIdNotValidException("Channel id is not valid");
        }

        var publicStatistic = youtubeService.getChannelStatistics(channelId);
        var response = StatisticMapper.INSTANCE.statisticEntityToStatisticResponse(publicStatistic);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "https://neptun.md")
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsByIdResponse> getChannelStatisticsAuthenticatedUser() {
        var publicStatistic = youtubeService.getChannelStatistics();
        var response = StatisticMapper.INSTANCE.statisticEntityToStatisticResponse(publicStatistic);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "https://neptun.md")
    @GetMapping("/profile")
    public ResponseEntity<ProfileByIdResponse> getProfileById() {
        var userProfile = youtubeService.getCurrentUserProfile();
        var response = UserProfileMapper.INSTANCE.userProfileEntityToProfileByIdResponse(userProfile);

        return ResponseEntity.ok(response);
    }

}
