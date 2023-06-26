package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.ApplicationQuestionDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionListVo {
    List<ApplicationQuestion> applicationQuestions;
    List<ApplicationQuestionDetail> applicationQuestionDetails;

    @Builder
    private QuestionListVo(List<ApplicationQuestion> applicationQuestions, List<ApplicationQuestionDetail> applicationQuestionDetails) {
        this.applicationQuestions = applicationQuestions;
        this.applicationQuestionDetails = applicationQuestionDetails;
    }

    public static QuestionListVo of(List<ApplicationQuestion> applicationQuestions,
                                    List<ApplicationQuestionDetail> applicationQuestionDetails) {
        return QuestionListVo.builder()
                .applicationQuestions(applicationQuestions)
                .applicationQuestionDetails(applicationQuestionDetails)
                .build();
    }
}
