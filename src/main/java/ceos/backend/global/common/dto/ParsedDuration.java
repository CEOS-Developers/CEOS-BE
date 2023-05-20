package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ParsedDuration {
    private String date;
    private String duration;

    @Builder
    private ParsedDuration(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public static ParsedDuration of(String date, String duration) {
        return ParsedDuration.builder()
                .date(date)
                .duration(duration)
                .build();
    }
}
