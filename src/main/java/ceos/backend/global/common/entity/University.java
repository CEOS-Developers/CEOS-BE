package ceos.backend.global.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
@Getter
@AllArgsConstructor
public enum University {
    SOGANG("서강대학교", "SOGANG"),
    YONSEI("연세대학교", "YONSEI"),
    EWHA("이화여자대학교", "EWHA"),
    HONGIK("홍익대학교", "HONGIK");

    private final String university;
    @JsonValue private final String key;

    @JsonCreator
    public static University parsing(String inputValue) {
        return Stream.of(University.values())
                .filter(category -> category.getUniversity().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
