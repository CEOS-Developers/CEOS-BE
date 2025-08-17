package ceos.backend.domain.recruitment.dto;


import ceos.backend.domain.recruitment.domain.Recruitment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentDTO {
    private int generation;
    private String prodStudyUrl;
    private String designStudyUrl;
    private String devStudyUrl;
    private LocalDateTime startDateDoc;
    private LocalDateTime endDateDoc;
    private LocalDateTime resultDateDoc;
    private LocalDateTime startDateInterview;
    private LocalDateTime endDateInterview;
    private LocalDateTime resultDateFinal;
    private String openChatUrl;
    private LocalDate otDate;
    private LocalDate ideathonDate;
    private LocalDate hackathonDate;
    private LocalDate demodayDate;
    private LocalDate startMTDate;
    private LocalDate endMTDate;

    @Builder
    public RecruitmentDTO(
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
            LocalDate startMTDate,
            LocalDate endMTDate) {
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
        this.startMTDate = startMTDate;
        this.endMTDate = endMTDate;
    }

    public static RecruitmentDTO from(Recruitment recruitment) {
        return RecruitmentDTO.builder()
                .generation(recruitment.getGeneration())
                .prodStudyUrl(recruitment.getProdStudyUrl())
                .designStudyUrl(recruitment.getDesignStudyUrl())
                .devStudyUrl(recruitment.getDevStudyUrl())
                .startDateDoc(recruitment.getStartDateDoc())
                .endDateDoc(recruitment.getEndDateDoc())
                .resultDateDoc(recruitment.getResultDateDoc())
                .startDateInterview(recruitment.getStartDateInterview())
                .endDateInterview(recruitment.getEndDateInterview())
                .resultDateFinal(recruitment.getResultDateFinal())
                .openChatUrl(recruitment.getOpenChatUrl())
                .otDate(recruitment.getOtDate())
                .ideathonDate(recruitment.getIdeathonDate())
                .hackathonDate(recruitment.getHackathonDate())
                .demodayDate(recruitment.getDemodayDate())
                .startMTDate(recruitment.getStartMTDate())
                .endMTDate(recruitment.getEndMTDate())
                .build();
    }
}
