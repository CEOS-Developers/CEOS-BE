package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsSESMail {
    private int n;

    @Builder
    private AwsSESMail(int n) {
        this.n = n;
    }

    public static AwsSESMail from(int n) {
        return AwsSESMail.builder()
                .n(n)
                .build();
    }
}
