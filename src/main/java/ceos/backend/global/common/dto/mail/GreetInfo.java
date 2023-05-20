package ceos.backend.global.common.dto.mail;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GreetInfo {
    private String name;
    private String generation;

    @Builder
    private GreetInfo(String name, String generation) {
        this.name = name;
        this.generation = generation;
    }

    public static GreetInfo from(CreateApplicationRequest request) {
        return GreetInfo.builder()
                .generation(Integer.toString(request.getApplicationDetailVo().getGeneration()))
                .name(request.getApplicantInfoVo().getName())
                .build();
    }
}
