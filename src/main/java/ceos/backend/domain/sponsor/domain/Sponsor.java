package ceos.backend.domain.sponsor.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Sponsor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sponsor_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    private String imageUrl;

    // 생성자
    @Builder
    private Sponsor(String name,
                    String imageUrl)
    {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // 정적 팩토리 메서드
}
