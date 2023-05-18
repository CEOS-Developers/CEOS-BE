package ceos.backend.global.common.dto.mail;

import ceos.backend.domain.application.vo.ApplicantInfoVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UuidInfo {
    private String name;
    private String uuid;

    @Builder
    private UuidInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public static UuidInfo of(ApplicantInfoVo applicantInfoVo, String UUID) {
        return UuidInfo.builder()
                .uuid(UUID)
                .name(applicantInfoVo.getName())
                .build();
    }
}
