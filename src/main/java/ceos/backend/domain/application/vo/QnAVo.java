package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicationAnswer;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QnAVo {
    @JsonUnwrapped
    private QuestionVo questionVo;

    @JsonUnwrapped
    private AnswerVo answerVo;

    @Builder
    private QnAVo(QuestionVo questionVo, AnswerVo answerVo) {
        this.questionVo = questionVo;
        this.answerVo = answerVo;
    }

    public static QnAVo of(ApplicationQuestion question, ApplicationAnswer answer) {
        return QnAVo.builder()
//                .questionVo(QuestionVo.of(question))
                .answerVo(AnswerVo.of(question, answer))
                .build();
    }
}
