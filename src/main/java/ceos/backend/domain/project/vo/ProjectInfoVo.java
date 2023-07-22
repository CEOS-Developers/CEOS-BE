package ceos.backend.domain.project.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ProjectInfoVo {
    @Schema()
    @NotEmpty(message = "프로젝트 이름를 입력해주세요.")
    private String name;

    @Schema()
    @NotEmpty(message = "설명을 입력해주세요.")
    private String description;

    @Schema()
    @NotEmpty(message = "기수를 입력해주세요.")
    private int generation;

    //    @Builder
    //    public ProjectInfoVo(String name,
    //                         String description,
    //                         int generation
    //    ) {
    //        this.name = name;
    //        this.description = description;
    //        this.generation = generation;
    //    }
    //
    //    public static ProjectInfoVo from(Project project) {
    //        return ProjectInfoVo.builder()
    //                .name(project.getName())
    //                .description(project.getDescription())
    //                .generation(project.getGeneration())
    //                .build();
    //    }
}
