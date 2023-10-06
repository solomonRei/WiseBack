package com.teamback.wise.domain.repositories;

import com.teamback.wise.domain.entities.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<StatisticEntity, String> {

}
