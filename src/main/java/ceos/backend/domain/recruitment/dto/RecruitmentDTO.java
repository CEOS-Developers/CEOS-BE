package ceos.backend.domain.recruitment.dto;


import ceos.backend.domain.recruitment.domain.Recruitment;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentDTO {
    private int generation;
    private String prodStudyUrl;
    private String designStudyUrl;
    private String devStudyUrl;
    private LocalDate startDateDoc;
    private LocalDate endDateDoc;
    private LocalDate resultDateDoc;
    private LocalDate startDateInterview;
    private LocalDate endDateInterview;
    private LocalDate resultDateFinal;
    private String openChatUrl;
    private LocalDate otDate;
    private LocalDate demodayDate;

    @Builder
    public RecruitmentDTO(
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
                .demodayDate(recruitment.getDemodayDate())
                .build();
    }
}
