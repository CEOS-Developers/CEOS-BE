package ceos.backend.domain.project.domain;

import ceos.backend.domain.project.vo.ProjectUrlVo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_url_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectUrlCategory category;

    @NotNull
    private String linkUrl;

    @ManyToOne(fetch = FetchType.LAZY)  // Cascade
    @JsonBackReference
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    private ProjectUrl(
            ProjectUrlCategory category,
            String linkUrl,
            Project project
    ) {
        this.category = category;
        this.linkUrl = linkUrl;
        this.project = project;
    }

    // 정적 팩토리 메서드
    public static ProjectUrl of(ProjectUrlVo projectUrlVo, Project project) {
        return ProjectUrl.builder()
                .category(projectUrlVo.getCategory())
                .linkUrl(projectUrlVo.getLinkUrl())
                .project(project)
                .build();
    }
}
