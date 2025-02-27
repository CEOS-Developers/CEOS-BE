package ceos.backend.domain.application.dto.response;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.ParsedDurationConvertor;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetResultResponse {
    private Pass pass;

    private int generation;

    private String name;

    private Part part;

    @JsonUnwrapped private ParsedDuration parsedDuration;

    private LocalDate otDate;

    private String openChatUrl;

    private boolean attendanceStatus;

    @Builder
    private GetResultResponse(
            Pass pass,
            int generation,
            String name,
            Part part,
            ParsedDuration parsedDuration,
            LocalDate otDate,
            boolean attendanceStatus,
            String openChatUrl) {
        this.pass = pass;
        this.generation = generation;
        this.name = name;
        this.part = part;
        this.parsedDuration = parsedDuration;
        this.otDate = otDate;
        this.attendanceStatus = attendanceStatus;
        this.openChatUrl = openChatUrl;
    }

    public static GetResultResponse toDocumentResult(
            Application application, Recruitment recruitment) {
        ParsedDuration duration = null;

        if (application.getInterviewDatetime() != null) {
            duration = ParsedDurationConvertor.parsingDuration(application.getInterviewDatetime());
        }

        return GetResultResponse.builder()
                .pass(application.getDocumentPass())
                .generation(recruitment.getGeneration())
                .name(application.getApplicantInfo().getName())
                .part(application.getApplicationDetail().getPart())
                .parsedDuration(duration)
                .otDate(recruitment.getOtDate())
                .attendanceStatus(application.isInterviewCheck())
                .openChatUrl(recruitment.getOpenChatUrl())
                .build();
    }

    public static GetResultResponse toFinalResult(
            Application application, Recruitment recruitment) {
        return GetResultResponse.builder()
                .pass(application.getFinalPass())
                .generation(recruitment.getGeneration())
                .name(application.getApplicantInfo().getName())
                .part(application.getApplicationDetail().getPart())
                .parsedDuration(
                        ParsedDurationConvertor.parsingDuration(application.getInterviewDatetime()))
                .otDate(recruitment.getOtDate())
                .openChatUrl(recruitment.getOpenChatUrl())
                .attendanceStatus(application.isFinalCheck())
                .build();
    }
}
