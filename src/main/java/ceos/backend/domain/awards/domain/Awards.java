package ceos.backend.domain.awards.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "awards_id")
    private Long id;

    @NotNull private int generation;

    @NotNull
    @Size(max = 100)
    private String content;

    // 생성자
    @Builder
    private Awards(int generation, String content) {
        this.generation = generation;
        this.content = content;
    }

    // 정적 팩토리 메서드
    public static Awards of(int generation, String content) {
        return Awards.builder().generation(generation).content(content).build();
    }
}
