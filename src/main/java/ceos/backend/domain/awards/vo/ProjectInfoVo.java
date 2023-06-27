package ceos.backend.domain.awards.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectInfoVo {

    private int generation;
    private String name;
    private String description;

    @Builder
    public ProjectInfoVo(int generation, String name, String description) {
        this.generation = generation;
        this.name = name;
        this.description = description;
    }

    public static ProjectInfoVo of(int generation, String name, String description){
        return ProjectInfoVo.builder()
                .generation(generation)
                .name(name)
                .description(description)
                .build();
    }
}
