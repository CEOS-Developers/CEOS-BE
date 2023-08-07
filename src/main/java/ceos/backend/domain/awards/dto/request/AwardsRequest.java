package ceos.backend.domain.awards.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwardsRequest {
    @Schema(defaultValue = "17", description = "수상 팀 활동 기수")
    @NotNull(message = "수상 팀의 활동 기수를 입력해주세요")
    @Valid
    private int generation;

    @Schema(description = "활동 시작 시기")
    @NotNull(message = "활동 시작 시기를 입력해주세요")
    @Valid
    private LocalDate startDate;

    @Schema(description = "수상 기록 리스트")
    @NotEmpty(message = "수상 기록을 입력해주세요")
    @Valid
    private List<String> content;

    @Builder
    public AwardsRequest(int generation, LocalDate startDate, List<String> content) {
        this.generation = generation;
        this.startDate = startDate;
        this.content = content;
    }

    public static AwardsRequest of(int generation, LocalDate startDate, List<String> content) {
        return AwardsRequest.builder()
                .generation(generation)
                .startDate(startDate)
                .content(content)
                .build();
    }
}
