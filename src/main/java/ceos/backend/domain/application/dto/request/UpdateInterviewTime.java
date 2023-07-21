package ceos.backend.domain.application.dto.request;

import ceos.backend.global.common.annotation.ValidDuration;
import ceos.backend.global.common.dto.ParsedDuration;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateInterviewTime {
    @JsonUnwrapped
    private ParsedDuration parsedDuration;
}
