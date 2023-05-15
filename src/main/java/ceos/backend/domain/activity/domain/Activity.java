package ceos.backend.domain.activity.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 255)
    private String content;

    @NotNull
    private String imageUrl;

    @NotNull
    @Column(unique = true)
    private int number;


    // 생성자
    @Builder
    private Activity(String name,
                  String content,
                  String imageUrl,
                  int number) {

        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
        this.number = number;
    }

    // 정적 팩토리 메서드
}
