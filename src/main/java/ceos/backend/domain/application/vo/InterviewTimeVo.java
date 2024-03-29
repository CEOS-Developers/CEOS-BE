package ceos.backend.domain.application.vo;


import ceos.backend.global.common.dto.ParsedDuration;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InterviewTimeVo {
    private boolean isUnavailable;

    @JsonUnwrapped private ParsedDuration parsedDuration;

    @Builder
    private InterviewTimeVo(boolean isUnavailable, ParsedDuration parsedDuration) {
        this.isUnavailable = isUnavailable;
        this.parsedDuration = parsedDuration;
    }

    public static InterviewTimeVo of(boolean isUnavailable, ParsedDuration parsedDuration) {
        return InterviewTimeVo.builder()
                .isUnavailable(isUnavailable)
                .parsedDuration(parsedDuration)
                .build();
    }
}
