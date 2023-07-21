package ceos.backend.global.common.dto;


import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsSESMail {
    private CreateApplicationRequest createApplicationRequest;
    private List<ApplicationQuestion> applicationQuestions;
    private int generation;
    private String UUID;

    @Builder
    private AwsSESMail(
            CreateApplicationRequest createApplicationRequest,
            List<ApplicationQuestion> applicationQuestions,
            int generation,
            String UUID) {
        this.createApplicationRequest = createApplicationRequest;
        this.applicationQuestions = applicationQuestions;
        this.generation = generation;
        this.UUID = UUID;
    }

    public static AwsSESMail of(
            CreateApplicationRequest createApplicationRequest,
            List<ApplicationQuestion> applicationQuestions,
            int generation,
            String UUID) {
        return AwsSESMail.builder()
                .createApplicationRequest(createApplicationRequest)
                .applicationQuestions(applicationQuestions)
                .generation(generation)
                .UUID(UUID)
                .build();
    }
}
