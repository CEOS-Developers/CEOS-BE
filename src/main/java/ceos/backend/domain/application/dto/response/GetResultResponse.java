package ceos.backend.domain.application.dto.response;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.util.ParsedDurationConvertor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetResultResponse {
    private Pass pass;

    private int generation;

    private String name;

    @JsonUnwrapped
    private ParsedDuration parsedDuration;

    private LocalDate otDate;

    @Builder
    private GetResultResponse(Pass pass, int generation, String name, ParsedDuration parsedDuration, LocalDate otDate) {
        this.pass = pass;
        this.generation = generation;
        this.name = name;
        this.parsedDuration = parsedDuration;
        this.otDate = otDate;
    }

    public static GetResultResponse toDocumentResult(Application application, Recruitment recruitment) {
        return GetResultResponse.builder().pass(application.getDocumentPass())
                .generation(recruitment.getGeneration())
                .name(application.getApplicantInfo().getName())
                .parsedDuration(ParsedDurationConvertor.parsingDuration(application.getInterviewDatetime()))
                .otDate(recruitment.getOtDate()).build();
    }

    public static GetResultResponse toFinalResult(Application application, Recruitment recruitment) {
        return GetResultResponse.builder().pass(application.getFinalPass()).generation(recruitment.getGeneration())
                .name(application.getApplicantInfo().getName())
                .parsedDuration(ParsedDurationConvertor.parsingDuration(application.getInterviewDatetime()))
                .otDate(recruitment.getOtDate()).build();
    }
}
