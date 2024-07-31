package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.domain.Application;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetFinalAvailability {
    private boolean finalAvailability; // 활동 가능 여부
    private String reason; // 활동 불가능 사유

    @Builder
    public GetFinalAvailability(boolean finalCheck, String reason) {
        this.finalAvailability = finalCheck;
        this.reason = reason;
    }

    public static GetFinalAvailability of(Application application) {
        return GetFinalAvailability.builder()
                .finalAvailability(application.isFinalCheck())
                .reason(application.getUnableReason())
                .build();
    }

}
