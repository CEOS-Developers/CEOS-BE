package ceos.backend.domain.application.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    F("여성"),

    M("남성");

    @JsonValue private final String gender;

    @JsonCreator
    public static Gender parsing(String inputValue) {
        return Stream.of(Gender.values())
                .filter(category -> category.getGender().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
