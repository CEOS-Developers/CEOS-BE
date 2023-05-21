package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.global.common.annotation.DateFormat;
import ceos.backend.global.common.annotation.ValidTimeDuration;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UpdateApplicationQuestion {
    @Valid
    private List<AnswerVo> commonAnswers;

    @Valid
    private List<AnswerVo> productAnswers;

    @Valid
    private List<AnswerVo> designAnswers;

    @Valid
    private List<AnswerVo> frontendAnswers;

    @Valid
    private List<AnswerVo> backendAnswers;

    @ArraySchema(schema = @Schema(description = "면접 날짜", type = "string",
            defaultValue = "2023.03.20"))
    @Valid
    private List<@DateFormat LocalDate> dates;

    @ArraySchema(schema = @Schema(description = "면접 시간", type = "string",
            defaultValue = "00:00:00 - 00:00:00"))
    @Valid
    private List<@ValidTimeDuration String> times;
}
