package ceos.backend.domain.recruitment.domain;


import ceos.backend.domain.admin.exception.NotAllowedToModify;
import ceos.backend.domain.application.exception.exceptions.WrongGeneration;
import ceos.backend.domain.recruitment.dto.RecruitmentDTO;
import ceos.backend.domain.recruitment.exception.*;
import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @NotNull private int generation;

    @NotNull private String prodStudyUrl;

    @NotNull private String designStudyUrl;

    @NotNull private String devStudyUrl;

    @NotNull private LocalDate startDateDoc;

    @NotNull private LocalDate endDateDoc;

    @NotNull private LocalDate resultDateDoc;

    @NotNull private LocalDate startDateInterview;

    @NotNull private LocalDate endDateInterview;

    @NotNull private LocalDate resultDateFinal;

    @NotNull private String openChatUrl;

    @NotNull private LocalDate otDate;

    @NotNull private LocalDate demodayDate;

    private LocalDateTime applicationExcelCreatedAt;

    // 생성자
    @Builder
    private Recruitment(
            int generation,
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
            LocalDate demodayDate,
            LocalDateTime applicationExcelCreatedAt) {
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
        this.applicationExcelCreatedAt = applicationExcelCreatedAt;
    }

    public void updateRecruitment(RecruitmentDTO recruitmentDTO) {
        this.generation = recruitmentDTO.getGeneration();
        this.prodStudyUrl = recruitmentDTO.getProdStudyUrl();
        this.designStudyUrl = recruitmentDTO.getDesignStudyUrl();
        this.devStudyUrl = recruitmentDTO.getDevStudyUrl();
        this.startDateDoc = recruitmentDTO.getStartDateDoc();
        this.endDateDoc = recruitmentDTO.getEndDateDoc();
        this.resultDateDoc = recruitmentDTO.getResultDateDoc();
        this.startDateInterview = recruitmentDTO.getStartDateInterview();
        this.endDateInterview = recruitmentDTO.getEndDateInterview();
        this.resultDateFinal = recruitmentDTO.getResultDateFinal();
        this.openChatUrl = recruitmentDTO.getOpenChatUrl();
        this.otDate = recruitmentDTO.getOtDate();
        this.demodayDate = recruitmentDTO.getDemodayDate();
    }

    public void updateApplicationExcelCreatedAt(LocalDateTime createdAt) {
        this.applicationExcelCreatedAt = createdAt;
    }

    // Validation 관련
    public void validateGeneration(int generation) {
        if (generation != this.generation) {
            throw WrongGeneration.EXCEPTION;
        }
    }

    public void validateBetweenStartDateDocAndEndDateDoc(LocalDate now) {
        if (now.compareTo(this.getStartDateDoc()) < 0) {
            throw NotApplicationDuration.EXCEPTION;
        }
        if (now.isAfter(this.getEndDateDoc())) {
            throw NotApplicationDuration.EXCEPTION;
        }
    }

    public void validateFinalResultAbleDuration(LocalDate now) {
        if (now.compareTo(this.resultDateFinal.plusDays(5)) >= 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) < 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
    }

    public void validateBetweenStartDateDocAndResultDateDoc(LocalDate now) {
        if (now.compareTo(this.startDateDoc) < 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateDoc) >= 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
    }

    public void validateBetweenResultDateDocAndResultDateFinal(LocalDate now) {
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
