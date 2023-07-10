package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.global.common.annotation.ValidDuration;
import ceos.backend.global.common.annotation.ValidQuestionOrder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateApplicationQuestion {
    @ValidQuestionOrder
    @Valid
    private List<QuestionVo> commonQuestions;

    @ValidQuestionOrder
    @Valid
    private List<QuestionVo> productQuestions;

    @ValidQuestionOrder
    @Valid
    private List<QuestionVo> designQuestions;

    @ValidQuestionOrder
    @Valid
    private List<QuestionVo> frontendQuestions;

    @ValidQuestionOrder
    @Valid
    private List<QuestionVo> backendQuestions;

    @ArraySchema(schema = @Schema(description = "면접 시간",
            type = "2023.03.20 00:00:00 - 2023.03.20 00:30:00",
            defaultValue = "2023.03.20 00:00:00 - 2023.03.20 00:30:00"))
    @Valid
    private List<@ValidDuration String> times;
}
