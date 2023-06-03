package ceos.backend.domain.project.dto.response;

import ceos.backend.domain.project.vo.ProjectBriefInfoVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProjectsResponse {

    private List<ProjectBriefInfoVo> projectBriefInfoVos;

    @Builder
    private GetProjectsResponse(List<ProjectBriefInfoVo> ProjectBriefInfoVos) {
        this.projectBriefInfoVos = ProjectBriefInfoVos;
    }

    public static GetProjectsResponse from(List<ProjectBriefInfoVo> ProjectBriefInfoVos) {
        return GetProjectsResponse.builder()
                .ProjectBriefInfoVos(ProjectBriefInfoVos)
                .build();
    }
}
