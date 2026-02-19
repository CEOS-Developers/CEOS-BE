package ceos.backend.domain.application.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
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
                .filter(category -> category.getCheck().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
