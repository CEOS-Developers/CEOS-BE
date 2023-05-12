package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ApplicationAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_answer_id")
    private Long id;

    // Question : Answer = 1:1 (단방향)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_question_id")
    private ApplicationQuestion applicationQuestion;

    // Application : Answer : N:1 (단방향)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String answer;

    // 생성자
    @Builder
    private ApplicationAnswer(ApplicationQuestion applicationQuestion,
                              Application application,
                              String answer)
    {
        this.applicationQuestion = applicationQuestion;
        this.application = application;
        this.answer = answer;
    }

    // 정적 팩토리 메서드
}
