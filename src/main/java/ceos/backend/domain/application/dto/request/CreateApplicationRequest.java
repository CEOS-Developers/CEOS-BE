package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.vo.*;
import ceos.backend.global.common.annotation.DateFormat;
import ceos.backend.global.common.annotation.DateTimeFormat;
import ceos.backend.global.common.annotation.ValidDuration;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.Part;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateApplicationRequest {
    @JsonUnwrapped
    private ApplicantInfoVo applicantInfoVo;

    @Schema(type = "string",
            pattern = "yyyy.MM.dd",
            defaultValue = "2023.03.20",
            description = "ot 날짜")
    @NotNull(message = "ot 날짜를 입력해주세요")
    @DateFormat
    private LocalDate otDate;

    @Schema(type = "string",
            pattern = "yyyy.MM.dd",
            defaultValue = "2023.03.20",
            description = "데모데이 날짜")
    @NotNull(message = "데모데이 날짜를 입력해주세요")
    @DateFormat
    private LocalDate demodayDate;

    @Schema(defaultValue = "구르기", description = "지원자 다른 활동")
    @NotEmpty(message = "지원자 다른 활동을 입력해주세요")
    private String otherActivities;

    @Schema(defaultValue = "백엔드", description = "지원자 파트")
    @ValidEnum(target = Part.class)
    private Part part;

    @Valid
    private List<AnswerVo> commonAnswers;

    @Valid
    private List<AnswerVo> partAnswers;

    @Valid
    private List<InterviewDateTimesVo> unableTimes;
}
