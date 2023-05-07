package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SlackErrorMessage {
    private int n;

    @Builder
    private SlackErrorMessage(int n) {
        this.n = n;
    }

    public static SlackErrorMessage of(int n) {
        return SlackErrorMessage.builder()
                .n(n)
                .build();
    }
}
