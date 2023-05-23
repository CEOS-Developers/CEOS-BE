package ceos.backend.domain.recruitment.dto.response;

import ceos.backend.domain.recruitment.domain.Recruitment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetRecruitmentResponse {
    private int generation;
    private String prodImg;
    private String designImg;
    private String devImg;
    private String prodStudyUrl;
    private String designStudyUrl;
    private String devStudyUrl;
    private LocalDate startDateDoc;
    private LocalDate endDateDoc;
    private LocalDate resultDateDoc;
    private LocalDate startDateInterview;
    private LocalDate endDateInterview;
    private LocalDate resultDateFinal;
    private LocalDate otDate;
    private LocalDate demodayDate;

    public static GetRecruitmentResponse from(Recruitment recruitment) {
        return GetRecruitmentResponse.builder()
                .generation(recruitment.getGeneration())
                .prodImg(recruitment.getProdImg())
                .designImg(recruitment.getDesignImg())
                .devImg(recruitment.getDevImg())
                .prodStudyUrl(recruitment.getProdStudyUrl())
                .designStudyUrl(recruitment.getDesignStudyUrl())
                .devStudyUrl(recruitment.getDevStudyUrl())
                .startDateDoc(recruitment.getStartDateDoc())
                .endDateDoc(recruitment.getEndDateDoc())
                .resultDateDoc(recruitment.getResultDateDoc())
                .startDateInterview(recruitment.getStartDateInterview())
                .endDateInterview(recruitment.getEndDateInterview())
                .resultDateFinal(recruitment.getResultDateFinal())
                .otDate(recruitment.getOtDate())
                .demodayDate(recruitment.getDemodayDate())
                .build();
    }
}
