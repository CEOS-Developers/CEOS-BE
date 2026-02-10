package ceos.backend.domain.application.domain;


import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.exceptions.*;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Embedded private ApplicantInfo applicantInfo;

    @Embedded private ApplicationDetail applicationDetail;

    private String interviewDatetime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AvailableCheck interviewCheck = AvailableCheck.UNDECIDED;

    @Size(max = 100)
    private String unableReason;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Pass documentPass;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AvailableCheck finalCheck = AvailableCheck.UNDECIDED; // 활동 가능 여부

    @NotNull
    @Enumerated(EnumType.STRING)
    private Pass finalPass; // 최종 합격 결과

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationAnswer> applicationAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<ApplicationInterview> applicationInterviews = new ArrayList<>();

    @Builder
    private Application(ApplicantInfo applicantInfo, ApplicationDetail applicationDetail) {
        this.applicantInfo = applicantInfo;
        this.applicationDetail = applicationDetail;
        this.interviewDatetime = null;
        this.documentPass = Pass.FAIL;
        this.finalPass = Pass.FAIL;
    }

    // 정적 팩토리 메서드
    public static Application of(
            CreateApplicationRequest createApplicationRequest, int generation, String UUID) {
        return Application.builder()
                .applicantInfo(
                        ApplicantInfo.of(createApplicationRequest.getApplicantInfoVo(), UUID))
                .applicationDetail(ApplicationDetail.of(createApplicationRequest, generation))
                .build();
    }

    public boolean isInterviewAvailable() {
        return interviewCheck == AvailableCheck.AVAILABLE;
    }

    public boolean isInterviewChecked() {
        return interviewCheck != AvailableCheck.UNDECIDED;
    }

    public boolean isFinalAvailable() {
        return finalCheck == AvailableCheck.AVAILABLE;
    }

    public boolean isFinalChecked() {
        return finalCheck != AvailableCheck.UNDECIDED;
    }

    public void addApplicationAnswerList(List<ApplicationAnswer> applicationAnswers) {
        this.applicationAnswers = applicationAnswers;
    }

    public void addApplicationInterviewList(List<ApplicationInterview> applicationInterviews) {
        this.applicationInterviews = applicationInterviews;
    }

    public void updateInterviewCheck(AvailableCheck check) {
        this.interviewCheck = check;
    }

    public void updateUnableReason(String reason) { this.unableReason = reason; }

    public void updateFinalCheck(AvailableCheck check) {
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

    public void validateFinalPass() {
        if (this.finalPass == Pass.FAIL) {
            throw NotPassFinal.EXCEPTION;
        }
    }

    public void validateNotFinalCheck() {
        if (this.isFinalChecked()) {
            throw AlreadyCheckFinal.EXCEPTION;
        }
    }

    public void validateNotInterviewCheck() {
        if (this.isInterviewChecked()) {
            throw AlreadyCheckInterview.EXCEPTION;
        }
    }
}
