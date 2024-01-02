package ceos.backend.domain.retrospect.dto.request;


import ceos.backend.domain.retrospect.domain.Retrospect;
import ceos.backend.global.common.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateRetrospectRequest {
    @NotBlank(message = "회고 제목을 입력해주세요")
    @Schema(type = "string", description = "회고 제목")
    private String title;

    @NotBlank(message = "회고 내용을 입력해주세요")
    @Schema(type = "string", description = "회고 내용")
    private String content;

    @NotBlank(message = "작성자를 입력해주세요")
    @Schema(type = "string", description = "회고 제목")
    private String writer;

    @NotNull(message = "기수를 입력해주세요")
    @Positive
    @Schema(type = "integer", description = "기수")
    private Integer generation;

    @NotNull(message = "파트를 입력해주세요")
    @Schema(type = "Part", description = "파트")
    private Part part;

    public Retrospect toEntity() {
        return Retrospect.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .generation(generation)
                .part(part)
                .build();
    }
}
