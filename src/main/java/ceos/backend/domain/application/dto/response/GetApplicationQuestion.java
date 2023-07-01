package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.vo.InterviewDateTimesVo;
import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.domain.application.vo.QuestionWithIdVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetApplicationQuestion {
    private List<QuestionWithIdVo> commonQuestions;
    private List<QuestionWithIdVo> productQuestions;
    private List<QuestionWithIdVo> designQuestions;
    private List<QuestionWithIdVo> frontendQuestions;
    private List<QuestionWithIdVo> backendQuestions;
    private List<InterviewDateTimesVo> times;

    @Builder
    private GetApplicationQuestion(List<QuestionWithIdVo> commonQuestions, List<QuestionWithIdVo> productQuestions,
                                  List<QuestionWithIdVo> designQuestions, List<QuestionWithIdVo> frontendQuestions,
                                  List<QuestionWithIdVo> backendQuestions, List<InterviewDateTimesVo> times) {
        this.commonQuestions = commonQuestions;
        this.productQuestions = productQuestions;
        this.designQuestions = designQuestions;
        this.frontendQuestions = frontendQuestions;
        this.backendQuestions = backendQuestions;
        this.times = times;
    }

    public static GetApplicationQuestion of(List<QuestionWithIdVo> commonQuestions, List<QuestionWithIdVo> productQuestions,
                                            List<QuestionWithIdVo> designQuestions, List<QuestionWithIdVo> frontendQuestions,
                                            List<QuestionWithIdVo> backendQuestions, List<InterviewDateTimesVo> times) {
        return GetApplicationQuestion.builder()
                .commonQuestions(commonQuestions)
                .productQuestions(productQuestions)
                .designQuestions(designQuestions)
                .frontendQuestions(frontendQuestions)
                .backendQuestions(backendQuestions)
                .times(times)
                .build();
    }
}
