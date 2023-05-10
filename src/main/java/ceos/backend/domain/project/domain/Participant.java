package ceos.backend.domain.project.domain;

import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    @Size(max = 30)
    private String name;


    // 생성자
    @Builder
    private Participant(Part part, String name) {
        this.part = part;
        this.name = name;
    }

    // 정적 팩토리 메서드
}
