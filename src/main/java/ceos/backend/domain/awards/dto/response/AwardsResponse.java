package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AwardsResponse {

    private Long id;
    private int generation;
    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsResponse(Long id, int generation,
                           String content,
                           LocalDate startDate)
    {
        this.id = id;
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsResponse to(Awards awards){
        return AwardsResponse.builder()
                .id(awards.getId())
                .generation(awards.getGeneration())
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
