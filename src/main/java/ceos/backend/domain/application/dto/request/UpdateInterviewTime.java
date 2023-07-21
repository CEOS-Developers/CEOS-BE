package ceos.backend.domain.application.dto.request;


import ceos.backend.global.common.dto.ParsedDuration;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class UpdateInterviewTime {
    @JsonUnwrapped private ParsedDuration parsedDuration;
}
