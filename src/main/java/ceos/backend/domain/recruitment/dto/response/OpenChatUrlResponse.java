package ceos.backend.domain.recruitment.dto.response;


import ceos.backend.domain.recruitment.domain.Recruitment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenChatUrlResponse {
    private String openChatUrl;

    public static OpenChatUrlResponse from(Recruitment recruitment) {
        return OpenChatUrlResponse.builder().openChatUrl(recruitment.getOpenChatUrl()).build();
    }
}
