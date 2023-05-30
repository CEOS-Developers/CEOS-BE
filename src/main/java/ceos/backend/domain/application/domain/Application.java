package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.NotPassDocument;
import ceos.backend.domain.application.exception.SamePassStatus;
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

import java.util.ArrayList;
import java.util.List;

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

    private String interviewDatetime;

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

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationAnswer> applicationAnswers = new ArrayList<>();

    @Builder
    private Application(ApplicantInfo applicantInfo, ApplicationDetail applicationDetail) {
        this.applicantInfo = applicantInfo;
        this.applicationDetail = applicationDetail;
        this.interviewDatetime = null;
        this.documentPass = Pass.FAIL;
        this.finalPass = Pass.FAIL;
    }

    // 정적 팩토리 메서드
    public static Application of(CreateApplicationRequest createApplicationRequest, String UUID) {
        return Application.builder()
                .applicantInfo(ApplicantInfo.of(createApplicationRequest.getApplicantInfoVo(), UUID))
                .applicationDetail(ApplicationDetail.from(createApplicationRequest.getApplicationDetailVo()))
                .build();
    }

    public void addApplicationAnswers(ApplicationAnswer answer) {
        applicationAnswers.add(answer);
        answer.setApplication(this);
    }

    public void updateInterviewCheck(boolean check) {
        this.interviewCheck = check;
    }

    public void updateFinalCheck(boolean check) {
        this.finalCheck = check;
    }

    public void updateDocumentPass(Pass pass) {
        if (this.documentPass == pass) {
            throw SamePassStatus.EXCEPTION;
        }
        this.documentPass = pass;
    }

    public void updateFinalPass(Pass pass) {
        if (this.finalPass == pass) {
            throw SamePassStatus.EXCEPTION;
        }
        this.finalPass = pass;
    }

    public void validateDocumentPass() {
        if (this.documentPass == Pass.FAIL) {
            throw NotPassDocument.EXCEPTION;
        }
    }

    public void updateInterviewTime(String interviewTime) {
        this.interviewDatetime = interviewTime;
    }
}
