package ceos.backend.domain.awards.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectInfoVo {

    private String name;
    private String description;

    @Builder
    public ProjectInfoVo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ProjectInfoVo of(String name, String description) {
        return ProjectInfoVo.builder().name(name).description(description).build();
    }
}
