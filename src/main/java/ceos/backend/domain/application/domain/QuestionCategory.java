package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.Part;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

public enum QuestionCategory {
    COMMON,
    PRODUCT,
    DESIGN,
    FRONTEND,
    BACKEND;

    public Boolean validateCategory(String inputValue) {
        return Stream.of(QuestionCategory.values())
                .anyMatch(category -> category.toString().equals(inputValue));
    }
}
