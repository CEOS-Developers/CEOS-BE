package ceos.backend.domain.project.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_image_id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private ProjectImageCategory category;

    @NotNull
    private String imageUrl;

    // 생성자
    @Builder
    private ProjectImage(ProjectImageCategory category,
                    String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // 정적 팩토리 메서드
}
