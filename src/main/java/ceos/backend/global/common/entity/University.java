package ceos.backend.global.common.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum University {
    SOGANG("서강대학교"),
    YONSEI("연세대학교"),
    EWHA("이화여자대학교"),
    HONGIK("홍익대학교"),
    NEWYORK("뉴욕대학교");

    @JsonValue private final String university;

    @JsonCreator
    public static University parsing(String inputValue) {
        return Stream.of(University.values())
                .filter(category -> category.getUniversity().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
