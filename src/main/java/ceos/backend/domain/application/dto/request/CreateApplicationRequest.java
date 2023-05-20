package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.application.vo.ApplicationDetailVo;
import ceos.backend.global.common.annotation.DateTimeFormat;
import ceos.backend.global.common.annotation.ValidDuration;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateApplicationRequest {
    @JsonUnwrapped
    private ApplicantInfoVo applicantInfoVo;

    @JsonUnwrapped
    private ApplicationDetailVo applicationDetailVo;

    @Valid
    private List<AnswerVo> commonAnswers;

    @Valid
    private List<AnswerVo> partAnswers;

    @ArraySchema(schema = @Schema(description = "불가능 시간 선택 ", type = "string",
            defaultValue = "2023.03.20 00:00:00 - 2023.03.20 00:00:00"))
    @Valid
    private List<@ValidDuration String> unableTimes;
}
