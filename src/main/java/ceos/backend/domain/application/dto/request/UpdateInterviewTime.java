package ceos.backend.domain.application.dto.request;

import ceos.backend.global.common.annotation.ValidDuration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateInterviewTime {
    @Schema(description = "면접 시간 선택 ",
            type = "string",
            defaultValue = "2023.03.20 00:00:00 - 2023.03.20 00:00:00")
    @ValidDuration
    private String interviewTime;
}
