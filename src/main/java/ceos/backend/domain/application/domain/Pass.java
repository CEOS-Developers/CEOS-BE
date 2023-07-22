package ceos.backend.domain.application.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Pass {
    PASS("합격"),

    FAIL("불합격");

    @JsonValue private final String result;

    @JsonCreator
    public static Pass parsing(String inputValue) {
        return Stream.of(Pass.values())
                .filter(category -> category.getResult().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
