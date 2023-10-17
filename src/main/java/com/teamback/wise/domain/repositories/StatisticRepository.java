package com.teamback.wise.domain.repositories;

import com.teamback.wise.domain.entities.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticRepository extends JpaRepository<StatisticEntity, String> {
    StatisticEntity findByUser_Id(UUID userId);
}
