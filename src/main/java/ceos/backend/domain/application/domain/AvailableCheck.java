package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.exception.exceptions.InvalidAvailableCheck;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AvailableCheck {
    UNDECIDED("미입력"),
    AVAILABLE("가능"),
    UNAVAILABLE("불가능");

    @JsonValue private final String check;

    @JsonCreator
    public static AvailableCheck parsing(String inputValue) {
        return Stream.of(AvailableCheck.values())
                .filter(category -> category.name().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> InvalidAvailableCheck.EXCEPTION);
    }
}
