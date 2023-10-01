package ceos.backend.global.common.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Part {
    PRODUCT("기획"),
    DESIGN("디자인"),
    FRONTEND("프론트엔드"),
    BACKEND("백엔드");

    @JsonValue private final String part;

    @JsonCreator
    public static Part parbsing(String inputValue) {
        return Stream.of(Part.values())
                .filter(category -> category.getPart().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
