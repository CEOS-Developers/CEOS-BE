package ceos.backend.domain.faq.domain;

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
    @Size(max = 20)
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
}
