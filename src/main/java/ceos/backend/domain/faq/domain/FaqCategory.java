package ceos.backend.domain.faq.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FaqCategory {
    RECRUIT("리쿠르팅"), ACTIVITY("활동"), PART("파트");

    @JsonValue
    private final String faqCategory;

    @JsonCreator
    public static FaqCategory parsing(String inputValue) {
        return Stream.of(FaqCategory.values())
                .filter(category -> category.getFaqCategory().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
