package com.teamback.wise.services.youtube.Impl;

import com.teamback.wise.domain.entities.StatisticEntity;
import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.entities.UserProfileEntity;
import com.teamback.wise.domain.mappers.StatisticMapper;
import com.teamback.wise.domain.mappers.UserProfileMapper;
import com.teamback.wise.domain.repositories.StatisticRepository;
import com.teamback.wise.domain.repositories.UserProfileRepository;
import com.teamback.wise.domain.repositories.UserRepository;
import com.teamback.wise.exceptions.youtube.ChannelStatisticNotFoundException;
import com.teamback.wise.exceptions.youtube.UserProfileNotFoundException;
import com.teamback.wise.services.youtube.YouTubeService;
import com.teamback.wise.services.youtube.api.YouTubeApiKeyService;
import com.teamback.wise.services.youtube.api.YouTubeOauth2KeyService;
import com.teamback.wise.utils.AuthenticatedUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class YouTubeServiceImpl implements YouTubeService {

    private final StatisticRepository statisticRepository;

    private final UserProfileRepository userProfileRepository;

    private final UserRepository userRepository;

    private final YouTubeApiKeyService youTubeApiKeyService;

    private final YouTubeOauth2KeyService youtubeServiceSecured;

    private final AuthenticatedUserUtils authenticatedUserUtils;

    @Override
    public StatisticEntity getChannelStatistics(String channelId) {
        var statisticFromDB = statisticRepository.findByYoutubeChannelId(channelId);

        log.info("Getting Statistic for channel: " + channelId);

        return statisticFromDB.orElseGet(() -> updateOrInsertChannelStatistics(channelId));

    }

    @Override
    public StatisticEntity getChannelStatistics() {
        var authenticatedUser = authenticatedUserUtils.getCurrentUserEntity();
        var statistic = authenticatedUser.getStatistic();
        if (statistic != null) {
            log.info("Getting Statistic for channel: " + statistic.getYoutubeChannelId());
            return statistic;
        }

        log.error("Channel statistics not found");
        throw new ChannelStatisticNotFoundException("Channel statistics not found");
    }

    @Override
    public StatisticEntity updateOrInsertChannelStatistics(String channelId) {
        var channelStatistics = youTubeApiKeyService.getChannelStatistics(channelId);

        if (channelStatistics != null) {
            log.info("Updating or inserting Statistic for channel: " + channelId);
            var authenticatedUser = authenticatedUserUtils.getCurrentUserEntity();
            var statisticEntity = StatisticMapper.INSTANCE.statisticResponseToStatisticEntity(channelStatistics.getItems().get(0), authenticatedUser);

            authenticatedUser.setStatistic(statisticEntity);
            authenticatedUser = userRepository.save(authenticatedUser);

            return authenticatedUser.getStatistic();
        }

        log.error("Channel statistics not found");
        throw new ChannelStatisticNotFoundException("Channel statistics not found");
    }

    @Override
    public void updateOrInsertChannelStatistics(UserEntity user) {
        var channelStatistics = youTubeApiKeyService.getChannelStatistics(user.getYoutubeChannelId());

        if (channelStatistics != null) {
            log.info("Updating or inserting Statistic for channel: " + user.getYoutubeChannelId());
            var statisticEntity = StatisticMapper.INSTANCE.statisticResponseToStatisticEntity(channelStatistics.getItems().get(0), user);

            user.setStatistic(statisticEntity);
            userRepository.save(user);
            log.info("Successfully updated or inserted Statistic for channel: " + user.getYoutubeChannelId());
        }
    }

    public UserProfileEntity getCurrentUserProfile() {
        var authenticatedUser = authenticatedUserUtils.getCurrentUserEntity();
        var channelId = authenticatedUser.getYoutubeChannelId();

        if (authenticatedUser.getUserProfile() != null &&
                authenticatedUser.getUserProfile().getUpdatedAt().isBefore(ZonedDateTime.now().minusMinutes(30))) {
            return updateOrInsertUserProfile(channelId);
        }

        return getUserProfile(channelId);
    }

    public UserProfileEntity getUserProfile(String channelId) {
        var userProfileFromDB = userProfileRepository.findByYoutubeChannelId(channelId);

        log.info("Getting User Profile for channel: " + channelId);

        return userProfileFromDB.orElseGet(() -> updateOrInsertUserProfile(channelId));
    }

    @Override
    public UserProfileEntity updateOrInsertUserProfile(String channelId) {
        var userProfile = youTubeApiKeyService.getChannelProfile(channelId);

        if (userProfile != null) {
            log.info("Updating or inserting User Profile for channel: " + channelId);
            var authenticatedUser = authenticatedUserUtils.getCurrentUserEntity();

            var userProfileEntity = userProfileRepository.findByYoutubeChannelId(channelId);

            if (userProfileEntity.isEmpty()) {
                var userProfileEntityNew = UserProfileMapper.INSTANCE.channelResponseToUserProfileEntity(userProfile.getItems().get(0), authenticatedUser);
                authenticatedUser.setUserProfile(userProfileEntityNew);
                authenticatedUser = userRepository.save(authenticatedUser);

                return authenticatedUser.getUserProfile();
            }

            authenticatedUser.setUserProfile(userProfileEntity.get());
            authenticatedUser = userRepository.save(authenticatedUser);

            return authenticatedUser.getUserProfile();
        }

        log.error("User profile not found");
        throw new UserProfileNotFoundException("User profile not found");
    }


    @Override
    public void updateOrInsertChannelCountryStatistics(String accessToken) {
        var channelStatistics = youtubeServiceSecured.getChannelStatistics(accessToken);

        log.info("Updating or inserting Statistic for channel: ");
    }

    @Override
    public boolean isChannelIdValid(String channelId) {
        var regexPattern = "^[A-Za-z0-9_-]+$";
        var pattern = Pattern.compile(regexPattern);
        return pattern.matcher(channelId).matches();
    }
}
