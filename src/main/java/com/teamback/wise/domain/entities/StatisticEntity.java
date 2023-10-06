package com.teamback.wise.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistics")
public class StatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "view_count", nullable = false)
    @ColumnDefault("0")
    private int viewCount;

    @Column(name = "video_count", nullable = false)
    @ColumnDefault("0")
    private int videoCount;

    @Column(name = "subscriber_count", nullable = false)
    @ColumnDefault("0")
    private int subscriberCount;

    @Column(name = "created_at", nullable = false)
    private LocalTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalTime updatedAt;

    @OneToMany(mappedBy = "statistic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatisticsCountryEntity> statisticsCountry;

    @OneToOne
    private UserEntity user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalTime.now();
    }
}
