package ceos.backend.domain.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackTest {
    private String n;

    @Builder
    private SlackTest(String n) {
        this.n = n;
    }

    public static SlackTest of(String n) {
        return SlackTest.builder()
                .n(n)
                .build();
    }
}
