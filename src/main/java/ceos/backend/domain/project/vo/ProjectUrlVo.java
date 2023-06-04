package ceos.backend.domain.project.vo;

import ceos.backend.domain.project.domain.ProjectUrl;
import ceos.backend.domain.project.domain.ProjectUrlCategory;
import ceos.backend.global.common.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectUrlVo {

    @Schema()
    @ValidEnum(target = ProjectUrlCategory.class)
    private ProjectUrlCategory category;

    @Schema()
    @NotEmpty(message = "Url을 입력해주세요.")
    private String linkUrl;

    @Builder
    public ProjectUrlVo(ProjectUrlCategory category, String linkUrl) {
        this.category = category;
        this.linkUrl = linkUrl;
    }

    public static ProjectUrlVo from(ProjectUrl projectUrl) {
        return ProjectUrlVo.builder()
                .category(projectUrl.getCategory())
                .linkUrl(projectUrl.getLinkUrl())
                .build();
    }
}
