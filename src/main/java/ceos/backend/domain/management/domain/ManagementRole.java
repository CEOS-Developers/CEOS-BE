package ceos.backend.domain.management.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagementRole {
    PRESIDENCY("회장단"),
    ADVISOR("고문"), // advisor, presidency 분리할지 논의 필요
    GENERAL_AFFAIRS("총무"),
    PART_LEADER("파트장"),
    MANAGEMENT("운영진"),
    MENTOR("멘토"),
    ACTIVE_MENTOR("active 멘토"),
    OB_MENTOR("OB 멘토");

    @JsonValue private final String managementRole;

    @JsonCreator
    public static ManagementRole parsing(String inputValue) {
        return Stream.of(ManagementRole.values())
                .filter(category -> category.getManagementRole().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
