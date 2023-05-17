package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.domain.Gender;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.application.vo.ApplicationDetailVo;
import ceos.backend.global.common.annotation.*;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.entity.University;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
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

    @Schema(description = "불가능 시간 선택 \"yyyy.MM.dd HH:mm:ss\" 형식으로 적어주세요!")
    @DateTimeFormat
    private List<LocalDateTime> unableTimes;
}
