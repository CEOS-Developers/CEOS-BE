package ceos.backend.domain.activity.domain;

import ceos.backend.domain.activity.dto.ActivityDto;
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

    // 생성자
    @Builder
    private Activity(String name, String content, String imageUrl) {

        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // 정적 팩토리 메서드
    public static Activity from(ActivityDto activityDto) {
        return Activity.builder()
                .name(activityDto.getName())
                .content(activityDto.getContent())
                .imageUrl(activityDto.getImageUrl())
                .build();
    }

    // Update
    public void updateActivity(ActivityDto activityDto) {
        name = activityDto.getName();
        content = activityDto.getContent();
        imageUrl = activityDto.getImageUrl();
    }
}
