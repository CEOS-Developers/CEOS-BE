package ceos.backend.domain.activity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ActivityRequest {
    @Schema(defaultValue = "데모데이", description = "활동 이름")
    @NotEmpty(message = "활동 이름을 입력해주세요")
    @Valid
    private String name;

    @Schema(defaultValue = "데모데이 짱입니다", description = "활동 설명")
    @NotEmpty(message = "활동 설명을 입력해주세요")
    @Valid
    private String content;

    @Schema(defaultValue = "demoday.jpg", description = "활동 이미지")
    @NotEmpty(message = "활동 이미지를 입력해주세요")
    @Valid
    private String imageUrl;

    @Builder
    private ActivityRequest(String name, String content, String imageUrl) {
        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static ActivityRequest of(String name, String content, String imageUrl) {
        return ActivityRequest.builder().name(name).content(content).imageUrl(imageUrl).build();
    }
}
