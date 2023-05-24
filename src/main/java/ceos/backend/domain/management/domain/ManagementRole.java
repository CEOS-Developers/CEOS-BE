package ceos.backend.domain.management.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ManagementRole {
    MANAGEMENT("운영진"), MENTOR("멘토");

    @JsonValue
    private final String managementRole;

    @JsonCreator
    public static ManagementRole parsing(String inputValue) {
        return Stream.of(ManagementRole.values())
                .filter(category -> category.getManagementRole().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
