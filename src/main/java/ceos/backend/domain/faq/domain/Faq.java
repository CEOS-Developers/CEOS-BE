package ceos.backend.domain.faq.domain;

import ceos.backend.domain.faq.vo.FaqVo;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Faq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FaqCategory category;

    @NotNull
    @Size(max = 255)
    private String question;

    @NotNull
    @Size(max = 255)
    private String answer;

    // 생성자
    @Builder
    private Faq(FaqCategory category,
                   String question,
                   String answer)
    {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    // 정적 팩토리 메서드
    public static Faq from(FaqVo faqVo) {
        return Faq.builder()
                .category(faqVo.getCategory())
                .question(faqVo.getQuestion())
                .answer(faqVo.getAnswer())
                .build();
    }
}
