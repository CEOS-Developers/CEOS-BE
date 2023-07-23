package ceos.backend.domain.admin.dto.response;


import ceos.backend.domain.admin.vo.AdminBriefInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAdminsResponse {
    private List<AdminBriefInfoVo> adminBriefInfoVos;
    private PageInfo pageInfo;

    @Builder
    private GetAdminsResponse(List<AdminBriefInfoVo> adminBriefInfoVos, PageInfo pageInfo) {
        this.adminBriefInfoVos = adminBriefInfoVos;
        this.pageInfo = pageInfo;
    }

    public static GetAdminsResponse of(
            List<AdminBriefInfoVo> adminBriefInfoVos, PageInfo pageInfo) {
        return GetAdminsResponse.builder()
                .adminBriefInfoVos(adminBriefInfoVos)
                .pageInfo(pageInfo)
                .build();
    }
}
