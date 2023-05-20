package ceos.backend.domain.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateInterviewAttendanceRequest {
    @Schema(defaultValue = "true", description = "면접 가능 여부")
    @NotNull
    private boolean available;

    @Schema(defaultValue = "null", description = "면접 불가능 사유")
    private String reason;
}
