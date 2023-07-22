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

    public static GreetInfo of(CreateApplicationRequest request, int generation) {
        return GreetInfo.builder()
                .generation(Integer.toString(generation))
                .name(request.getApplicantInfoVo().getName())
                .build();
    }
}
