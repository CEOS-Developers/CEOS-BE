package ceos.backend.domain.awards.dto;

import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AwardsDto {

    private int generation;
    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsDto(int generation,
                      String content,
                      LocalDate startDate)
    {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsDto to(Awards awards){
        return AwardsDto.builder()
                .generation(awards.getGeneration())
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
