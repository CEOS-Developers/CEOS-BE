package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.application.vo.ApplicationDetailVo;
import ceos.backend.global.common.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

    @Schema(description = "불가능 시간 선택 \"yyyy.MM.dd HH:mm:ss - yyyy.MM.dd HH:mm:ss\" 형식으로 적어주세요!")
    private List<String> unableTimes;
}
