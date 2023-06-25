package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ApplicationQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_question_id")
    private Long id;

    @NotNull
    private int number;

    @NotNull
    @Size(max = 255)
    private String question;

    @NotNull
    private boolean multiline;

    @OneToMany(mappedBy = "applicationQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationQuestionDetail> applicationQuestionDetails = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Builder
    private ApplicationQuestion(int number, String question, boolean multiline,
                                List<ApplicationQuestionDetail> applicationQuestionDetails,
                                QuestionCategory category) {
        this.number = number;
        this.question = question;
        this.multiline = multiline;
        this.applicationQuestionDetails = applicationQuestionDetails;
        this.category = category;
    }

    public static ApplicationQuestion of(QuestionVo questionVo, QuestionCategory category) {
        return ApplicationQuestion.builder()
                .category(category)
                .question(questionVo.getQuestion())
                .number(questionVo.getQuestionIndex())
                .build();
    }
}
