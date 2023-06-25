package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionWithIdVo {
    @JsonUnwrapped
    private QuestionVo questionVo;

    @Schema(defaultValue = "1", description = "질문 고유번호")
    private Long questionId;

    @Builder
    private QuestionWithIdVo(QuestionVo questionVo, Long questionId) {
        this.questionVo = questionVo;
        this.questionId = questionId;
    }

    public static QuestionWithIdVo of(ApplicationQuestion applicationQuestion,
                                   List<QuestionDetailVo> questionDetailVos) {
        return QuestionWithIdVo.builder()
                .questionVo(QuestionVo.of(applicationQuestion, questionDetailVos))
                .questionId(applicationQuestion.getId())
                .build();
    }
}
