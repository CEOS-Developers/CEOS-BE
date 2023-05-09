package ceos.backend.domain.awards.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
