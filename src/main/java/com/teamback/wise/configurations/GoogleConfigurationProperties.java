package com.teamback.wise.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleConfigurationProperties {

    @Value("${google.client-id}")
    private String ClientId;

    @Value("${google.application-name}")
    private String googleApplicationName;

    @Value("${youtube.api.keys.file.path}")
    private String youTubeKeysFilePath;

    @Value("${youtube.api.url}")
    private String youTubeApiUrl;

    @Value("${google.api.url}")
    private String googleApiUrl;
}
