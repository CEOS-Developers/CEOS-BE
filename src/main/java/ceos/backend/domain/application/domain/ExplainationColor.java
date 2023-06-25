package ceos.backend.domain.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ExplainationColor {
    BLUE("blue"),
    GRAY("gray");

    @JsonValue
    private final String color;

    @JsonCreator
    public static ExplainationColor parsing(String inputValue) {
        return Stream.of(ExplainationColor.values())
                .filter(category -> category.getColor().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
