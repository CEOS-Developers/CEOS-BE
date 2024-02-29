package ceos.backend.domain.subscriber.domain;


import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscriber extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    // 생성자
    @Builder
    private Subscriber(String email) {
        this.email = email;
    }

    public static Subscriber from(String email) {
        return Subscriber.builder()
                .email(email)
                .build();
    }
}
