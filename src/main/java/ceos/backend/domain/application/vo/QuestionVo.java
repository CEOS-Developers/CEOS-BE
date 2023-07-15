package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.ApplicationQuestionDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionVo {
    @Schema(defaultValue = "1", description = "질문 번호 (순서)")
    @NotNull(message = "질문 번호를 입력해주세요.")
    private int questionIndex;

    @Schema(defaultValue = "질문", description = "질문")
    @NotEmpty(message = "질문을 입력해주세요")
    private String question;

    @Schema(defaultValue = "false", description = "입력창 크기")
    @NotNull(message = "입력창 크기를 입력해주세요")
    private boolean multiline;

    @Valid
    private List<QuestionDetailVo> questionDetail;

    @Builder
    private QuestionVo(int questionIndex, String question, boolean multiline,
                       List<QuestionDetailVo> questionDetail) {
        this.questionIndex = questionIndex;
        this.question = question;
        this.multiline = multiline;
        this.questionDetail = questionDetail;
    }

    public static QuestionVo of(ApplicationQuestion applicationQuestion,
                                List<QuestionDetailVo> questionDetailVos) {
        return QuestionVo.builder()
                .question(applicationQuestion.getQuestion())
                .questionIndex(applicationQuestion.getNumber())
                .multiline(applicationQuestion.isMultiline())
                .questionDetail(questionDetailVos)
                .build();
    }
}
