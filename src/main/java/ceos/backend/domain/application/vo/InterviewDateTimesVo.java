package ceos.backend.domain.application.vo;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewDateTimesVo {
    @Schema(defaultValue = "2023/07/07", description = "날짜")
    private String date;

    @ArraySchema(schema = @Schema(description = "불가능 시간 선택 ",
            type = "00:00-00:30",
            defaultValue = "00:00-00:00"))
    private List<String> durations;

    @Builder
    private InterviewDateTimesVo(String date, List<String> durations) {
        this.date = date;
        this.durations = durations;
    }

    public static InterviewDateTimesVo of(String date, List<String> durations) {
        return InterviewDateTimesVo.builder()
                .date(date)
                .durations(durations)
                .build();
    }
}
