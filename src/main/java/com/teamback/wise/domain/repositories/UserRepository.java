package com.teamback.wise.domain.repositories;

import com.teamback.wise.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findByYoutubeChannelId(String channelId);
}
