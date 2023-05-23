package ceos.backend.domain.activity.domain;

import ceos.backend.domain.activity.dto.ActivityRequest;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 255)
    private String content;

    @NotNull
    private String imageUrl;

    @Builder
    private Activity(String name, String content, String imageUrl) {

        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static Activity from(ActivityRequest activityRequest) {
        return Activity.builder()
                .name(activityRequest.getName())
                .content(activityRequest.getContent())
                .imageUrl(activityRequest.getImageUrl())
                .build();
    }

    public void updateActivity(ActivityRequest activityRequest) {
        name = activityRequest.getName();
        content = activityRequest.getContent();
        imageUrl = activityRequest.getImageUrl();
    }
}
