package ceos.backend.global.common.dto;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AwsSESMail {
    private CreateApplicationRequest createApplicationRequest;
    private List<ApplicationQuestion> applicationQuestions;
    private String UUID;

    @Builder
    private AwsSESMail(CreateApplicationRequest createApplicationRequest,
                       List<ApplicationQuestion> applicationQuestions, String UUID) {
        this.createApplicationRequest = createApplicationRequest;
        this.applicationQuestions = applicationQuestions;
        this.UUID = UUID;
    }

    public static AwsSESMail of(CreateApplicationRequest createApplicationRequest,
                                  List<ApplicationQuestion> applicationQuestions, String UUID) {
        return AwsSESMail.builder()
                .createApplicationRequest(createApplicationRequest)
                .applicationQuestions(applicationQuestions)
                .UUID(UUID)
                .build();
    }
}
