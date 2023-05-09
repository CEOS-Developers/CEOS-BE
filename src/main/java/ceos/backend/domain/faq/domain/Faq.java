package ceos.backend.domain.faq.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @NotNull
    @Size(max = 20)
    private String category; // enum

    @NotNull
    @Size(max = 255)
    private String question;

    @NotNull
    @Size(max = 255)
    private String answer;
}
