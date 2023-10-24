package com.teamback.wise.domain.repositories;

import com.teamback.wise.domain.entities.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, String> {

}
