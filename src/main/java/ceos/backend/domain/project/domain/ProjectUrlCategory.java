package ceos.backend.domain.project.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectUrlCategory {
    SERVICE("서비스"),
    GITHUB("깃허브"),
    BEHANCE("비핸스"),
    INSTAGRAM("인스타");

    @JsonValue private final String projectUrlCategory;

    @JsonCreator
    public static ProjectUrlCategory parsing(String inputValue) {
        return Stream.of(ProjectUrlCategory.values())
                .filter(category -> category.getProjectUrlCategory().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
