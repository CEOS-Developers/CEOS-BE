package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Application extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Embedded
    private ApplicantInfo applicantInfo;

    @Embedded
    private ApplicationDetail applicationDetail;

    private LocalDateTime interviewDatetime;

    @NotNull
    @ColumnDefault("false")
    private boolean interviewCheck;

    @NotNull
    @ColumnDefault("false")
    private boolean documentPass;

    @NotNull
    @ColumnDefault("false")
    private boolean finalCheck;

    @NotNull
    @ColumnDefault("false")
    private boolean finalPass;



    // 생성자
//    @Builder

    // 정적 팩토리 메서드
}
