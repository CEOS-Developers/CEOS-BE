package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ApplicationInterview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_interview_id")
    private Long id;

    // Application : ApplicationInterview = N:1 (단방향)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    // Interview : ApplicationInterview = N:1 (단방향)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    // 생성자
    @Builder
    private ApplicationInterview(Application application,
                                 Interview interview)
    {
        this.application = application;
        this.interview = interview;
    }

    // 정적 팩토리 메서드
}
