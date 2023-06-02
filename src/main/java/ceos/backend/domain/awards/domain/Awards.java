package ceos.backend.domain.awards.domain;

import ceos.backend.domain.awards.dto.CreateAwardsRequest;
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
    public static Awards from(CreateAwardsRequest createAwardsRequest){
        return Awards.builder()
                .generation(createAwardsRequest.getGeneration())
                .content(createAwardsRequest.getContent())
                .startDate(createAwardsRequest.getStartDate())
                .build();
    }
}
