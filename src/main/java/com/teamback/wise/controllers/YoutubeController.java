package com.teamback.wise.controllers;

import com.teamback.wise.services.youtube.YouTubeService;
import com.teamback.wise.services.youtube.api.YouTubeApiKeyService;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/youtube")
public class YoutubeController {

    private final YouTubeApiKeyService youTubeApiKeyService;

    private final YouTubeOauth2KeyService youtubeServiceSecured;

    private final YouTubeService youtubeService;

    @GetMapping("/statistics/{channelId}")
    public ResponseEntity<Object> getChannelStatistics(@PathVariable String channelId) {
        if (!youtubeService.isChannelIdValid(channelId)) {
            return ResponseEntity.badRequest().body("Invalid channel id");
        }

        var response = youTubeApiKeyService.getChannelStatistics(channelId);
        var responseSecured = youtubeServiceSecured.getChannelStatistics(channelId);
//        //TODO: add response from youtubeService
        return ResponseEntity.ok(response);
    }
}
