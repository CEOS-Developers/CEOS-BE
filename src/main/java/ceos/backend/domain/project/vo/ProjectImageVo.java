package ceos.backend.domain.project.vo;


import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.domain.ProjectImageCategory;
import ceos.backend.global.common.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectImageVo {

    @Schema()
    @ValidEnum(target = ProjectImageCategory.class)
    private ProjectImageCategory category;

    @Schema() private String imageUrl;

    @Builder
    public ProjectImageVo(ProjectImageCategory category, String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static ProjectImageVo from(ProjectImage projectImage) {
        return ProjectImageVo.builder()
                .category(projectImage.getCategory())
                .imageUrl(projectImage.getImageUrl())
                .build();
    }
}
