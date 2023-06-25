package ceos.backend.domain.faq.dto;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.domain.FaqCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FaqDto {

    private FaqCategory category;
    private String question;
    private String answer;

    @Builder
    private FaqDto(FaqCategory category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public static FaqDto entityToDto(Faq faq) {
        return FaqDto.builder()
                .category(faq.getCategory())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }
}
