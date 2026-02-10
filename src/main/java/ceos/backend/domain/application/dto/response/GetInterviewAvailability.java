package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.AvailableCheck;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetInterviewAvailability {
    private AvailableCheck interviewAvailability; // 참여 가능 여부
    private String reason; // 참여 불가능 사유

    @Builder
    public GetInterviewAvailability(AvailableCheck interviewCheck, String reason) {
        this.interviewAvailability = interviewCheck;
        this.reason = reason;
    }

    public static GetInterviewAvailability of(Application application) {
        return GetInterviewAvailability.builder()
                .interviewAvailability(application.getInterviewCheck())
                .reason(application.getUnableReason())
                .build();
    }
}
