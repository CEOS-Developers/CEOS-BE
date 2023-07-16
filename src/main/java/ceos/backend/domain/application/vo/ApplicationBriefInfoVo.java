package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicantInfo;
import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.global.common.entity.Part;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationBriefInfoVo {
    @JsonUnwrapped
    private ApplicantInfo applicantInfo;

    private Part part;
    private Long id;
    private Pass documentPass;
    private Pass finalPass;
    private String interviewTime;

    @Builder
    private ApplicationBriefInfoVo(ApplicantInfo applicantInfo, Long id, Part part,
                                  Pass documentPass, Pass finalPass, String interviewTime) {
        this.applicantInfo = applicantInfo;
        this.id = id;
        this.part = part;
        this.documentPass = documentPass;
        this.finalPass = finalPass;
        this.interviewTime = interviewTime;
    }

    public static ApplicationBriefInfoVo from(Application application) {
        return ApplicationBriefInfoVo.builder()
                .applicantInfo(application.getApplicantInfo())
                .id(application.getId())
                .part(application.getApplicationDetail().getPart())
                .documentPass(application.getDocumentPass())
                .finalPass(application.getFinalPass())
                .interviewTime(application.getInterviewDatetime())
                .build();
    }
}
