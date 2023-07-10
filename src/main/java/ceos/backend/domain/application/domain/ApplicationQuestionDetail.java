package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.vo.QuestionDetailVo;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ApplicationQuestionDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_question_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_question_id")
    private ApplicationQuestion applicationQuestion;

    @NotNull
    private String explaination;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExplainationColor color;

    @Builder
    private ApplicationQuestionDetail(ApplicationQuestion applicationQuestion, String explaination,
                                     ExplainationColor color) {
        this.applicationQuestion = applicationQuestion;
        this.explaination = explaination;
        this.color = color;
    }

    public static ApplicationQuestionDetail of(ApplicationQuestion applicationQuestion,
                                               QuestionDetailVo questionDetailVo) {
        return ApplicationQuestionDetail.builder()
                .applicationQuestion(applicationQuestion)
                .color(questionDetailVo.getColor())
                .explaination(questionDetailVo.getExplaination())
                .build();
    }
}
