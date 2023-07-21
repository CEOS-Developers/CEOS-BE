package ceos.backend.domain.project.vo;

import static ceos.backend.domain.project.domain.ProjectImageCategory.THUMBNAIL;

import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.exception.DataNotFound;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectBriefInfoVo {

    private Long id;
    private String name;
    private String description;
    private int generation;
    private ProjectImage thumbnailImage;

    @Builder
    public ProjectBriefInfoVo(
            Long id, String name, String description, int generation, ProjectImage thumbnailImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.generation = generation;
        this.thumbnailImage = thumbnailImage;
    }

    public static ProjectBriefInfoVo from(Project project) {
        return ProjectBriefInfoVo.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .generation(project.getGeneration())
                .thumbnailImage(
                        project.getProjectImages().stream()
                                .filter(image -> image.getCategory().equals(THUMBNAIL))
                                .findFirst()
                                .orElseThrow(() -> DataNotFound.EXCEPTION))
                .build();
    }
}
