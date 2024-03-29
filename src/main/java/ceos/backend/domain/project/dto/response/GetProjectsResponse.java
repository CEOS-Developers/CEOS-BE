package ceos.backend.domain.project.dto.response;


import ceos.backend.domain.project.vo.ProjectBriefInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProjectsResponse {

    private List<ProjectBriefInfoVo> content;
    private PageInfo pageInfo;

    @Builder
    private GetProjectsResponse(List<ProjectBriefInfoVo> ProjectBriefInfoVos, PageInfo pageInfo) {
        this.content = ProjectBriefInfoVos;
        this.pageInfo = pageInfo;
    }

    public static GetProjectsResponse of(
            List<ProjectBriefInfoVo> ProjectBriefInfoVos, PageInfo pageInfo) {
        return GetProjectsResponse.builder()
                .ProjectBriefInfoVos(ProjectBriefInfoVos)
                .pageInfo(pageInfo)
                .build();
    }
}
