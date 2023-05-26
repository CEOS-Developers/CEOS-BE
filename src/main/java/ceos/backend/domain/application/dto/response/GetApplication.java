package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.vo.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetApplication {
    @JsonUnwrapped
    private ApplicantInfoVo applicantInfoVo;
    @JsonUnwrapped
    private ApplicationDetailVo applicationDetailVo;

    private List<QnAVo> commonQuestions;
    private List<QnAVo> partQuestions;

    private List<InterviewTimeVo> times;

    @Builder
    private GetApplication(ApplicantInfoVo applicantInfoVo, ApplicationDetailVo applicationDetailVo,
                          List<QnAVo> commonQuestions, List<QnAVo> partQuestions, List<InterviewTimeVo> times) {
        this.applicantInfoVo = applicantInfoVo;
        this.applicationDetailVo = applicationDetailVo;
        this.commonQuestions = commonQuestions;
        this.partQuestions = partQuestions;
        this.times = times;
    }

    public static GetApplication of(Application application, List<QnAVo> commonQuestions,
                                    List<QnAVo> partQuestions, List<InterviewTimeVo> times) {
        return GetApplication.builder()
                .applicantInfoVo(ApplicantInfoVo.from(application.getApplicantInfo()))
                .applicationDetailVo(ApplicationDetailVo.from(application.getApplicationDetail()))
                .commonQuestions(commonQuestions)
                .partQuestions(partQuestions)
                .times(times)
                .build();
    }
}
