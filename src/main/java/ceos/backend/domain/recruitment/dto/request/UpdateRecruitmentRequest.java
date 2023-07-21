package ceos.backend.domain.recruitment.dto.request;


import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UpdateRecruitmentRequest {
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
}
