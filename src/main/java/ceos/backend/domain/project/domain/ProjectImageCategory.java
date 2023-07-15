package ceos.backend.domain.project.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ProjectImageCategory {
    THUMBNAIL("썸네일"),
    DETAIL("상세");

    @JsonValue
    private final String projectImageCategory;

    @JsonCreator
    public static ProjectImageCategory parsing(String inputValue) {
        return Stream.of(ProjectImageCategory.values())
                .filter(category -> category.getProjectImageCategory().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
