package ceos.backend.domain.admin.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AdminRole {
    ROLE_ROOT("루트"),
    ROLE_ADMIN("운영진"),
    ROLE_ANONYMOUS("임시");

    @JsonValue
    private final String adminRole;

    @JsonCreator
    public static AdminRole parsing(String inputValue) {
        return Stream.of(AdminRole.values())
                .filter(category -> category.getAdminRole().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
