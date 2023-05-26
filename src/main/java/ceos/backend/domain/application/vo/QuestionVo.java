package ceos.backend.domain.application.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionVo {
    @Schema(defaultValue = "1", description = "질문 번호")
    @NotNull(message = "질문 번호를 입력해주세요.")
    private int questionIndex;

    @Schema(defaultValue = "질문", description = "질문")
    @NotEmpty(message = "질문을 입력해주세요")
    private String question;

    @Builder
    private QuestionVo(int questionIndex, String question) {
        this.questionIndex = questionIndex;
        this.question = question;
    }

    public static QuestionVo of(int questionIndex, String question) {
        return QuestionVo.builder()
                .question(question)
                .questionIndex(questionIndex)
                .build();
    }
}
