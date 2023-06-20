package ceos.backend.domain.admin.dto.response;

import ceos.backend.domain.admin.vo.AdminBriefInfoVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAdminsResponse {
    private List<AdminBriefInfoVo> adminBriefInfoVos;

    @Builder
    private GetAdminsResponse(List<AdminBriefInfoVo> adminBriefInfoVos) {
        this.adminBriefInfoVos = adminBriefInfoVos;
    }

    public static GetAdminsResponse from(List<AdminBriefInfoVo> adminBriefInfoVos) {
        return GetAdminsResponse.builder()
                .adminBriefInfoVos(adminBriefInfoVos)
                .build();
    }

}
