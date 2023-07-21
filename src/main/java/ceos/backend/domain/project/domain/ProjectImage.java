package ceos.backend.domain.project.domain;


import ceos.backend.domain.project.vo.ProjectImageVo;
import ceos.backend.global.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Enumerated(EnumType.STRING)
    private ProjectImageCategory category;

    @NotNull private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Cascade
    @JsonBackReference
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 생성자
    @Builder
    private ProjectImage(ProjectImageCategory category, String imageUrl, Project project) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.project = project;
    }

    // 정적 팩토리 메서드
    public static ProjectImage of(ProjectImageVo projectImageVo, Project project) {
        return ProjectImage.builder()
                .category(projectImageVo.getCategory())
                .imageUrl(projectImageVo.getImageUrl())
                .project(project)
                .build();
    }

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
