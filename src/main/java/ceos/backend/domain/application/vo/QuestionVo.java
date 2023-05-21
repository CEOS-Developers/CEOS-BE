package ceos.backend.domain.application.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuestionVo {
    @Schema(defaultValue = "1", description = "질문 번호")
    @NotNull(message = "질문 번호를 입력해주세요.")
    private int questionIndex;

    @Schema(defaultValue = "질문", description = "질문")
    @NotEmpty(message = "질문을 입력해주세요")
    private String question;
}
