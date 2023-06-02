package ceos.backend.domain.awards.dto;

import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AwardsDTO {

    private int generation;
    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsDTO(int generation,
                   String content,
                   LocalDate startDate)
    {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsDTO to(Awards awards){
        return AwardsDTO.builder()
                .generation(awards.getGeneration())
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
