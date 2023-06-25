package ceos.backend.domain.faq.dto;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.domain.FaqCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FaqDto {

    private Long id;
    private FaqCategory category;
    private String question;
    private String answer;

    @Builder
    private FaqDto(Long id, FaqCategory category, String question, String answer) {
        this.id = id;
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public static FaqDto entityToDto(Faq faq) {
        return FaqDto.builder()
                .id(faq.getId())
                .category(faq.getCategory())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }
}
