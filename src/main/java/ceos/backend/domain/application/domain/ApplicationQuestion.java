package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ApplicationQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_question_id")
    private Long id;

    @NotNull
    @Column
    private int number;

    @NotNull
    @Size(max = 255)
    private String question;

    @NotNull
    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    // 생성자
    @Builder
    private ApplicationQuestion(int number,
                                String question,
                                QuestionCategory category)
    {
        this.number = number;
        this.question = question;
        this.category = category;
    }

    // 정적 팩토리 메서드
}
