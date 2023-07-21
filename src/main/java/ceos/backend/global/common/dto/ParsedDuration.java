package ceos.backend.global.common.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParsedDuration {
    @Schema(type = "string", defaultValue = "2023/03/20", description = "면접 날짜")
    private String date;

    @Schema(type = "string", defaultValue = "12:00-12:30", description = "면접 시간")
    private String duration;

    @Builder
    private ParsedDuration(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public static ParsedDuration of(String date, String duration) {
        return ParsedDuration.builder().date(date).duration(duration).build();
    }
}
