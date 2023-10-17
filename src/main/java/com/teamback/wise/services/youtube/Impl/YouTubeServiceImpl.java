package com.teamback.wise.services.youtube.Impl;

import com.teamback.wise.services.youtube.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class YouTubeServiceImpl implements YouTubeService {

    public boolean isChannelIdValid(String channelId) {
        String regexPattern = "^[A-Za-z0-9_-]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        return pattern.matcher(channelId).matches();
    }
}
