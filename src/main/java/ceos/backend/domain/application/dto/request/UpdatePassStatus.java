package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.domain.Pass;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdatePassStatus {
    @Schema(defaultValue = "탈락", description = "합격 여부")
    private Pass pass;
}
