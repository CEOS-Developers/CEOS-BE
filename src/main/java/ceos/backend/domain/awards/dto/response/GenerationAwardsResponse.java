package ceos.backend.domain.awards.dto.response;


import ceos.backend.domain.awards.vo.ProjectInfoVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GenerationAwardsResponse {
    private int generation;
    private String startDate;
    private List<AwardsResponse> awards;
    private List<ProjectInfoVo> projects;

    @Builder
    public GenerationAwardsResponse(
            int generation,
            String startDate,
            List<AwardsResponse> awards,
            List<ProjectInfoVo> projects) {
        this.generation = generation;
        this.startDate = startDate;
        this.awards = awards;
        this.projects = projects;
    }

    public static GenerationAwardsResponse of(
            int generation,
            String startDate,
            List<AwardsResponse> awards,
            List<ProjectInfoVo> projects) {
        return GenerationAwardsResponse.builder()
                .generation(generation)
                .startDate(startDate)
                .awards(awards)
                .projects(projects)
                .build();
    }
}
