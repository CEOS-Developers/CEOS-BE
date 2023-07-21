package ceos.backend.global.common.dto;


import ceos.backend.domain.application.domain.Application;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SlackUnavailableReason {
    private Application application;
    private String reason;
    private boolean isFinal;

    @Builder
    private SlackUnavailableReason(Application application, String reason, boolean isFinal) {
        this.application = application;
        this.reason = reason;
        this.isFinal = isFinal;
    }

    public static SlackUnavailableReason of(
            Application application, String reason, boolean isFinal) {
        return SlackUnavailableReason.builder()
                .application(application)
                .reason(reason)
                .isFinal(isFinal)
                .build();
    }
}
