package com.teamback.wise.domain.repositories;

import com.teamback.wise.domain.entities.UserEntity;
import com.teamback.wise.domain.entities.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {
    Optional<UserProfileEntity> findByUser(UserEntity user);

    Optional<UserProfileEntity> findByYoutubeChannelId(String youtubeChannelId);

    Optional<UserProfileEntity> findByYoutubeHandle(String youtubeHandle);
}
