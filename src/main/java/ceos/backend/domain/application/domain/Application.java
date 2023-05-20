package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.global.common.annotation.DateTimeFormat;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Enumerated(EnumType.STRING)
    private Pass documentPass;

    @NotNull
    @ColumnDefault("false")
    private boolean finalCheck;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Pass finalPass;

    @Builder
    private Application(ApplicantInfo applicantInfo, ApplicationDetail applicationDetail) {
        this.applicantInfo = applicantInfo;
        this.applicationDetail = applicationDetail;
        this.interviewDatetime = null;
        this.documentPass = Pass.FAIL;
        this.finalPass = Pass.FAIL;
    }

    // 정적 팩토리 메서드
    public static Application from(CreateApplicationRequest createApplicationRequest, String UUID) {
        return Application.builder()
                .applicantInfo(ApplicantInfo.of(createApplicationRequest.getApplicantInfoVo(), UUID))
                .applicationDetail(ApplicationDetail.of(createApplicationRequest.getApplicationDetailVo()))
                .build();
    }
}
