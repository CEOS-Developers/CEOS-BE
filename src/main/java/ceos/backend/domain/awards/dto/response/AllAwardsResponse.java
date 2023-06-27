package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllAwardsResponse {
    private List<AwardsResponse> awards;
    private List<ProjectInfoVo> projects;
    PageInfo pageInfo;

    @Builder
    private AllAwardsResponse(List<AwardsResponse> awards, List<ProjectInfoVo> projects, PageInfo pageInfo) {
        this.awards = awards;
        this.projects = projects;
        this.pageInfo = pageInfo;
    }

    public static AllAwardsResponse of(List<AwardsResponse> awards, List<ProjectInfoVo> projects, PageInfo pageInfo) {
        return AllAwardsResponse.builder()
                .awards(awards)
                .projects(projects)
                .pageInfo(pageInfo)
                .build();
    }
}
