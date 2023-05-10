package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class InterviewDateInfo {
    private List<List<String>> notAvailabletime;
    private List<String> date;

    @Builder
    public InterviewDateInfo(List<List<String>> notAvailabletime, List<String> date) {
        this.notAvailabletime = notAvailabletime;
        this.date = date;
    }

    // TODO: 엔티티보고 재정의하기
    public static InterviewDateInfo of(List<List<String>> notAvailabletime, List<String> date) {
        return InterviewDateInfo.builder()
                .notAvailabletime(notAvailabletime)
                .date(date)
                .build();
    }
}
