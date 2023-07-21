package ceos.backend.domain.application.vo;


import ceos.backend.domain.application.domain.ApplicationQuestionDetail;
import ceos.backend.domain.application.domain.ExplainationColor;
import ceos.backend.global.common.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuestionDetailVo {

    @Schema(defaultValue = "설명", description = "질문 설명")
    @NotEmpty(message = "질문 설명을 입력해주세요")
    private String explaination;

    @Schema(defaultValue = "gray", description = "글자 색상")
    @ValidEnum(target = ExplainationColor.class)
    private ExplainationColor color;

    @Builder
    private QuestionDetailVo(String explaination, ExplainationColor color) {
        this.explaination = explaination;
        this.color = color;
    }

    public static QuestionDetailVo from(ApplicationQuestionDetail detail) {
        return QuestionDetailVo.builder()
                .explaination(detail.getExplaination())
                .color(detail.getColor())
                .build();
    }
}
