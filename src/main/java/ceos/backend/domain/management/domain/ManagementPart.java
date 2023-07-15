package ceos.backend.domain.management.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ManagementPart {
    CHAIRMAN("회장"), VICE_CHAIRMAN("부회장"), CO_PRESIDENT("공동회장"), PLAN("기획"), DESIGN("디자인"), FRONTEND("프론트엔드"), BACKEND("백엔드");

    @JsonValue
    private final String managementPart;

    @JsonCreator
    public static ManagementPart parsing(String inputValue) {
        return Stream.of(ManagementPart.values())
                .filter(category -> category.getManagementPart().equals(inputValue))
                .findFirst()
                .orElse(null);
    }

}
