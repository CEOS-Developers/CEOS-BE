package ceos.backend.domain.application.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
public class AnswerVo {
    @Schema(defaultValue = "1", description = "질문 고유번호")
    @NotNull(message = "질문의 고유번호를 입력해주세요")
    private Long questionId;

    @Schema(defaultValue = "대답", description = "질문 답변")
    @NotEmpty(message = "답변을 입력해주세요")
    private String answer;
}
