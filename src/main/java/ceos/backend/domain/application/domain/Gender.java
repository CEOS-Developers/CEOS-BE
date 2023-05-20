package ceos.backend.domain.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Gender {
    F("여성"),

    M("남성");

    @JsonValue
    private final String gender;

    @JsonCreator
    public static Gender parsing(String inputValue) {
        return Stream.of(Gender.values())
                .filter(category -> category.getGender().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
