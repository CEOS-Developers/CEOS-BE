package ceos.backend.domain.application.domain;


import ceos.backend.domain.application.vo.QuestionVo;
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

    @NotNull private int number;

    @NotNull
    @Size(max = 255)
    private String question;

    @NotNull private boolean multiline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Builder
    private ApplicationQuestion(
            int number, String question, boolean multiline, QuestionCategory category) {
        this.number = number;
        this.question = question;
        this.multiline = multiline;
        this.category = category;
    }

    public static ApplicationQuestion of(QuestionVo questionVo, QuestionCategory category) {
        return ApplicationQuestion.builder()
                .category(category)
                .question(questionVo.getQuestion())
                .number(questionVo.getQuestionIndex())
                .multiline(questionVo.isMultiline())
                .build();
    }
}
