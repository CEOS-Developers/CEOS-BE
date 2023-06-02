package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AwardsResponse {

    private int generation;
    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsResponse(int generation,
                           String content,
                           LocalDate startDate)
    {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsResponse to(Awards awards){
        return AwardsResponse.builder()
                .generation(awards.getGeneration())
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
