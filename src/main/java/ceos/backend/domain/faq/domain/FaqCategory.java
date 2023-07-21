package ceos.backend.domain.faq.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaqCategory {
    RECRUIT("RECRUIT", "리쿠르팅"),
    ACTIVITY("ACTIVITY", "활동"),
    PART("PART", "파트");

    @JsonValue private final String faqCategory;
    private final String label;

    @JsonCreator
    public static FaqCategory parsing(String inputValue) {
        return Stream.of(FaqCategory.values())
                .filter(category -> category.getFaqCategory().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
