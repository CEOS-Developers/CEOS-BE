package ceos.backend.domain.project.vo;

import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import lombok.Builder;
import lombok.Getter;

import static ceos.backend.domain.project.domain.ProjectImageCategory.PREVIEW;

@Getter
public class ProjectBriefInfoVo {

    private Long id;
    private String name;
    private String description;
    private int generation;
    private ProjectImage previewImage;

    @Builder
    public ProjectBriefInfoVo(
            Long id,
            String name,
            String description,
            int generation,
            ProjectImage previewImage
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.generation = generation;
        this.previewImage = previewImage;
    }

    public static ProjectBriefInfoVo from(Project project) {
        return ProjectBriefInfoVo.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .generation(project.getGeneration())
                .previewImage(project.getProjectImages().stream()
                        .filter(image -> image.getCategory().equals(PREVIEW))
                        .findFirst()
                        .orElseThrow(null))
                .build();
    }
}
