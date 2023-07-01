package ceos.backend.domain.application.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class InterviewDateTimesVo {
    private String date;
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
