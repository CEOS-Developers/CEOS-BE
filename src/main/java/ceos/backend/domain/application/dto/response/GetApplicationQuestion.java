package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.vo.QuestionVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetApplicationQuestion {
    private List<QuestionVo> commonQuestions;
    private List<QuestionVo> productQuestions;
    private List<QuestionVo> designQuestions;
    private List<QuestionVo> frontendQuestions;
    private List<QuestionVo> backendQuestions;
    private List<String> dates;
    private List<String> times;

    @Builder
    private GetApplicationQuestion(List<QuestionVo> commonQuestions, List<QuestionVo> productQuestions,
                                  List<QuestionVo> designQuestions, List<QuestionVo> frontendQuestions,
                                  List<QuestionVo> backendQuestions, List<String> dates, List<String> times) {
        this.commonQuestions = commonQuestions;
        this.productQuestions = productQuestions;
        this.designQuestions = designQuestions;
        this.frontendQuestions = frontendQuestions;
        this.backendQuestions = backendQuestions;
        this.dates = dates;
        this.times = times;
    }

    public static GetApplicationQuestion of(List<QuestionVo> commonQuestions, List<QuestionVo> productQuestions,
                                            List<QuestionVo> designQuestions, List<QuestionVo> frontendQuestions,
                                            List<QuestionVo> backendQuestions, List<String> dates,
                                            List<String> times) {
        return GetApplicationQuestion.builder()
                .commonQuestions(commonQuestions)
                .productQuestions(productQuestions)
                .designQuestions(designQuestions)
                .frontendQuestions(frontendQuestions)
                .backendQuestions(backendQuestions)
                .dates(dates)
                .times(times)
                .build();
    }
}
