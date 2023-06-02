package ceos.backend.domain.awards.dto;

import ceos.backend.global.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class CreateAwardsRequest {
    @Schema(defaultValue = "17", description = "수상 팀 활동 기수")
    @NotNull(message = "수상 팀의 활동 기수를 입력해주세요")
    @Valid
    private int generation;

    @Schema(defaultValue = "2023 예비창업패키지 최종선정", description = "수상 기록")
    @NotEmpty(message = "수상 기록을 입력해주세요")
    @Valid
    private String content;

    @Schema(defaultValue = "2023-03-01", description = "활동 시작 시기")
    @NotNull(message = "활동 시작 시기를 입력해주세요")
    @Valid
    private LocalDate startDate;

    @Builder
    private CreateAwardsRequest(int generation, String content, LocalDate startDate) {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static CreateAwardsRequest of(int generation, String content, LocalDate startDate){
        return CreateAwardsRequest.builder()
                .generation(generation)
                .content(content)
                .startDate(startDate)
                .build();
    }
}
