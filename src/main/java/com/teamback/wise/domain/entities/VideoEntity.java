package com.teamback.wise.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "videos")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "video_id", unique = true, nullable = false)
    private String videoId;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "possible_idea", length = 4000)
    private String possibleIdea;

    @Column(length = 4000)
    private String summary;

    @Column(columnDefinition = "jsonb")
    private String tags;

    @Column(name = "possible_tags", columnDefinition = "jsonb")
    private String possibleTags;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @ManyToMany(mappedBy = "videos")
    private Set<UserEntity> users = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
