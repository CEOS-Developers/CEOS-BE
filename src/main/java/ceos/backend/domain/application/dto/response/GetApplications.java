package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.ApplicantInfo;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.domain.application.vo.ApplicationBriefInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetApplications {
    private List<ApplicationBriefInfoVo> applicationBriefInfoVos;
    PageInfo pageInfo;

    @Builder
    private GetApplications(List<ApplicationBriefInfoVo> applicationBriefInfoVos, PageInfo pageInfo) {
        this.applicationBriefInfoVos = applicationBriefInfoVos;
        this.pageInfo = pageInfo;
    }

    public static GetApplications of(List<ApplicationBriefInfoVo> applicationBriefInfoVos,
                                     PageInfo pageInfo) {
        return GetApplications.builder()
                .applicationBriefInfoVos(applicationBriefInfoVos)
                .pageInfo(pageInfo)
                .build();
    }

}
