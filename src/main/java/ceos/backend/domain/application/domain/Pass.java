package ceos.backend.domain.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Pass {
    PASS("합격"),

    FAIL("탈락");

    @JsonValue
    private final String result;

    @JsonCreator
    public static Pass parsing(String inputValue) {
        return Stream.of(Pass.values())
                .filter(category -> category.getResult().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
