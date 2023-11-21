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

    @NotNull private LocalDateTime startDateDoc;

    @NotNull private LocalDateTime endDateDoc;

    @NotNull private LocalDateTime resultDateDoc;

    @NotNull private LocalDateTime startDateInterview;

    @NotNull private LocalDateTime endDateInterview;

    @NotNull private LocalDateTime resultDateFinal;

    @NotNull private String openChatUrl;

    @NotNull private LocalDate otDate;

    @NotNull private LocalDate ideathonDate;

    @NotNull private LocalDate hackathonDate;

    @NotNull private LocalDate demodayDate;

    private LocalDateTime applicationExcelCreatedAt;

    // 생성자
    @Builder
    private Recruitment(
            int generation,
            String prodStudyUrl,
            String designStudyUrl,
            String devStudyUrl,
            LocalDateTime startDateDoc,
            LocalDateTime endDateDoc,
            LocalDateTime resultDateDoc,
            LocalDateTime startDateInterview,
            LocalDateTime endDateInterview,
            LocalDateTime resultDateFinal,
            String openChatUrl,
            LocalDate otDate,
            LocalDate ideathonDate,
            LocalDate hackathonDate,
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
        this.ideathonDate = ideathonDate;
        this.hackathonDate = hackathonDate;
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
        this.ideathonDate = recruitmentDTO.getIdeathonDate();
        this.hackathonDate = recruitmentDTO.getHackathonDate();
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

    public void validateBetweenStartDateDocAndEndDateDoc(LocalDateTime now) {
        if (now.compareTo(this.getStartDateDoc()) < 0) {
            throw NotApplicationDuration.EXCEPTION;
        }
        if (now.isAfter(this.getEndDateDoc())) {
            throw NotApplicationDuration.EXCEPTION;
        }
    }

    public void validateFinalResultAbleDuration(LocalDateTime now) {
        if (now.compareTo(this.resultDateFinal.plusDays(5)) >= 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) < 0) {
            throw NotFinalResultCheckDuration.EXCEPTION;
        }
    }

    public void validateBetweenStartDateDocAndResultDateDoc(LocalDateTime now) {
        if (now.compareTo(this.startDateDoc) < 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateDoc) >= 0) {
            throw NotDocumentPassDuration.EXCEPTION;
        }
    }

    public void validateBetweenResultDateDocAndResultDateFinal(LocalDateTime now) {
        if (now.compareTo(this.resultDateDoc) < 0) {
            throw NotFinalPassDuration.EXCEPTION;
        }
        if (now.compareTo(this.resultDateFinal) >= 0) {
            throw NotFinalPassDuration.EXCEPTION;
        }
    }

    public void validateBeforeStartDateDoc(LocalDateTime now) {
        if (now.compareTo(this.startDateDoc) >= 0) {
            throw AlreadyApplicationDuration.EXCEPTION;
        }
    }

    public void validAmenablePeriod(LocalDateTime now) {
        if (now.compareTo(this.startDateDoc) >= 0 && now.compareTo(this.resultDateFinal) <= 0) {
            throw NotAllowedToModify.EXCEPTION;
        }
    }
}
