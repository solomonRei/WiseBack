package com.teamback.wise.configurations;

import com.teamback.wise.exceptions.youtube.ApiKeyNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Component
public class GoogleConfigurationProperties {

    @Value("${google.client-id}")
    private String ClientId;

    @Value("${google.application-name}")
    private String googleApplicationName;

    private final List<String> apiKeys = new ArrayList<>();

    {
        apiKeys.add("AIzaSyBce1XEywxePkRmQqOGU77OHrzkW2hmlfA");
    }

    public String getRandomKey() {
        Random random = new Random();
        var apiKeys = getApiKeys();
        var randomString = apiKeys.get(random.nextInt(apiKeys.size()));

        if (randomString == null) {
            throw new ApiKeyNotFoundException("No API keys found");
        }

        return randomString;
    }

}
