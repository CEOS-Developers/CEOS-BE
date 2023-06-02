package ceos.backend.domain.awards.domain;

import ceos.backend.domain.awards.dto.request.AwardsRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "awards_id")
    private Long id;

    @NotNull
    private int generation;

    @NotNull
    @Size(max = 100)
    private String content;

    @NotNull
    private LocalDate startDate;

    // 생성자
    @Builder
    private Awards(int generation,
                   String content,
                   LocalDate startDate)
    {
        this.generation = generation;
        this.content = content;
        this.startDate = startDate;
    }

    // 정적 팩토리 메서드
    public static Awards from(AwardsRequest awardsRequest){
        return Awards.builder()
                .generation(awardsRequest.getGeneration())
                .content(awardsRequest.getContent())
                .startDate(awardsRequest.getStartDate())
                .build();
    }

    public void updateAward(AwardsRequest awardsRequest){
        this.generation = awardsRequest.getGeneration();
        this.content = awardsRequest.getContent();
        this.startDate = awardsRequest.getStartDate();
    }
}
