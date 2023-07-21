package ceos.backend.domain.application.dto.response;


import ceos.backend.domain.application.vo.ApplicationBriefInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetApplications {
    private List<ApplicationBriefInfoVo> content;
    PageInfo pageInfo;

    @Builder
    private GetApplications(
            List<ApplicationBriefInfoVo> applicationBriefInfoVos, PageInfo pageInfo) {
        this.content = applicationBriefInfoVos;
        this.pageInfo = pageInfo;
    }

    public static GetApplications of(
            List<ApplicationBriefInfoVo> applicationBriefInfoVos, PageInfo pageInfo) {
        return GetApplications.builder()
                .applicationBriefInfoVos(applicationBriefInfoVos)
                .pageInfo(pageInfo)
                .build();
    }
}
