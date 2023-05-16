package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GreetInfo {
    private String name;
    private String generation;

    @Builder
    private GreetInfo(String name, String generation) {
        this.name = name;
        this.generation = generation;
    }

    // TODO: 엔티티보고 재정의하기
    public static GreetInfo of(String name, String generation) {
        return GreetInfo.builder()
                .generation(generation)
                .name(name)
                .build();
    }
}
