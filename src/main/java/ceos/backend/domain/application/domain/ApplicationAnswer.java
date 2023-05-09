package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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


}
