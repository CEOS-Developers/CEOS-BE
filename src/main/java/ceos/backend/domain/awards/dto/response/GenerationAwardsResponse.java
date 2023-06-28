package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GenerationAwardsResponse {
    private int generation;
    private List<AwardsResponse> awards;
    private List<ProjectInfoVo> projects;

    @Builder
    public GenerationAwardsResponse(int generation, List<AwardsResponse> awards, List<ProjectInfoVo> projects) {
        this.generation = generation;
        this.awards = awards;
        this.projects = projects;
    }

    public static GenerationAwardsResponse of(int generation, List<AwardsResponse> awards, List<ProjectInfoVo> projects) {
        return GenerationAwardsResponse.builder()
                .generation(generation)
                .awards(awards)
                .projects(projects)
                .build();
    }
}
