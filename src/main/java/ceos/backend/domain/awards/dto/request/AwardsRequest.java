package ceos.backend.domain.awards.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwardsRequest {
    @Schema(defaultValue = "17", description = "수상 팀 활동 기수")
    @NotNull(message = "수상 팀의 활동 기수를 입력해주세요")
    @Valid
    private int generation;

    @Schema(defaultValue = "2023 예비창업패키지 최종선정", description = "수상 기록")
    @NotEmpty(message = "수상 기록을 입력해주세요")
    @Valid
    private String content;

    @Schema(description = "활동 시작 시기")
    @NotNull(message = "활동 시작 시기를 입력해주세요")
    @Valid
    private LocalDate startDate;

    @Builder
    private AwardsRequest(int generation, String content, LocalDate startDate) {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsRequest of(int generation, String content, LocalDate startDate) {
        return AwardsRequest.builder()
                .generation(generation)
                .content(content)
                .startDate(startDate)
                .build();
    }
}
