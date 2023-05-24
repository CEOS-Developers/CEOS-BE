package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.ApplicantInfo;
import ceos.backend.domain.application.domain.Pass;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetApplications {
    @JsonUnwrapped
    private ApplicantInfo applicantInfo;

    private Long id;
    private Pass documentPass;
    private Pass finalPass;
    private String interviewTime;

    @Builder
    private GetApplications(ApplicantInfo applicantInfo, Long id, Pass documentPass,
                           Pass finalPass, String interviewTime) {
        this.applicantInfo = applicantInfo;
        this.id = id;
        this.documentPass = documentPass;
        this.finalPass = finalPass;
        this.interviewTime = interviewTime;
    }
}
