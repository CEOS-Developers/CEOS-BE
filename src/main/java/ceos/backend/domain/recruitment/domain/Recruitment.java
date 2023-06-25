package ceos.backend.domain.recruitment.domain;

import ceos.backend.domain.admin.exception.NotAllowedToModify;
import ceos.backend.domain.application.exception.WrongGeneration;
import ceos.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import ceos.backend.domain.recruitment.exception.*;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @NotNull
    private int generation;

    @NotNull
    private String prodStudyUrl;

    @NotNull
    private String designStudyUrl;

    @NotNull
    private String devStudyUrl;

    @NotNull
    private LocalDate startDateDoc;

    @NotNull
    private LocalDate endDateDoc;

    @NotNull
    private LocalDate resultDateDoc;

    @NotNull
    private LocalDate startDateInterview;

    @NotNull
    private LocalDate endDateInterview;

    @NotNull
    private LocalDate resultDateFinal;

    @NotNull
    private String openChatUrl;

    @NotNull
    private LocalDate otDate;

    @NotNull
    private LocalDate demodayDate;

    // private LocalDate hackatonDate;

    // 생성자
    @Builder
    private Recruitment(int generation,
                        String prodStudyUrl,
                        String designStudyUrl,
                        String devStudyUrl,
                        LocalDate startDateDoc,
                        LocalDate endDateDoc,
                        LocalDate resultDateDoc,
                        LocalDate startDateInterview,
                        LocalDate endDateInterview,
                        LocalDate resultDateFinal,
                        String openChatUrl,
                        LocalDate otDate,
                        LocalDate demodayDate) {
        this.generation = generation;
        this.prodStudyUrl = prodStudyUrl;
        this.designStudyUrl = designStudyUrl;
        this.devStudyUrl = devStudyUrl;
        this.startDateDoc = startDateDoc;
        this.endDateDoc = endDateDoc;
        this.resultDateDoc = resultDateDoc;
        this.startDateInterview = startDateInterview;
        this.endDateInterview = endDateInterview;
        this.resultDateFinal = resultDateFinal;
        this.openChatUrl = openChatUrl;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
    }


    public void updateRecruitment(UpdateRecruitmentRequest updateRecruitmentRequest) {
        this.generation = updateRecruitmentRequest.getGeneration();
        this.prodStudyUrl = updateRecruitmentRequest.getProdStudyUrl();
        this.designStudyUrl = updateRecruitmentRequest.getDesignStudyUrl();
        this.devStudyUrl = updateRecruitmentRequest.getDevStudyUrl();
        this.startDateDoc = updateRecruitmentRequest.getStartDateDoc();
        this.endDateDoc = updateRecruitmentRequest.getEndDateDoc();
        this.resultDateDoc = updateRecruitmentRequest.getResultDateDoc();
        this.startDateInterview = updateRecruitmentRequest.getStartDateInterview();
        this.endDateInterview = updateRecruitmentRequest.getEndDateInterview();
        this.resultDateFinal = updateRecruitmentRequest.getResultDateFinal();
        this.openChatUrl = updateRecruitmentRequest.getOpenChatUrl();
        this.otDate = updateRecruitmentRequest.getOtDate();
        this.demodayDate = updateRecruitmentRequest.getDemodayDate();
    }


    // Validation 관련
    public void validateGeneration(int generation) {
        if (generation != this.generation) {
            throw WrongGeneration.EXCEPTION;
        }
    }

    public void validateApplyDuration(LocalDate now) {
        if (now.compareTo(this.getStartDateDoc()) < 0) {
            throw NotApplicationDuration.EXCEPTION;
        }
        if (now.isAfter(this.getEndDateDoc())) {
            throw NotApplicationDuration.EXCEPTION;
        }
    }

    public void validateDocumentResultDuration(LocalDate now) {
        if (now.compareTo(this.resultDateDoc) < 0) {
            throw NotDocumentResultCheckDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) >= 0) {
            throw NotDocumentResultCheckDuration.EXCEPTION;
        }
    }

    public void validateFinalResultDuration(LocalDate now) {
        if (now.compareTo(this.resultDateFinal.plusDays(5)) >= 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) < 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
    }

    public void validateDocumentPassDuration(LocalDate now) {
        if (now.compareTo(this.startDateDoc) < 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateDoc) >= 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
    }

    public void validateFinalPassDuration(LocalDate now) {
        if (now.compareTo(this.resultDateDoc) < 0) {
            throw NotFinalPassDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) >= 0) {
            throw NotFinalPassDuration.EXCEPTION;
        }
    }

    public void validateBeforeStartDateDoc(LocalDate now) {
        if (now.compareTo(this.startDateDoc) >= 0) {
            throw AlreadyApplicationDuration.EXCEPTION;
        }
    }
  
    public void validAmenablePeriod(LocalDate now) {
      if (now.compareTo(this.startDateDoc) >= 0 && now.compareTo(this.resultDateFinal) <= 0) {
          throw NotAllowedToModify.EXCEPTION;
      }
    }
}
