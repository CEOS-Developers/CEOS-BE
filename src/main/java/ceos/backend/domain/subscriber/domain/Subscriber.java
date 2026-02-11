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

    @Size(max = 255)
    private String phoneNum;

    // 생성자
    @Builder
    private Subscriber(String email, String phoneNum) {
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public static Subscriber from(String email, String phoneNum) {
        return Subscriber.builder()
                .email(email)
                .phoneNum(phoneNum)
                .build();
    }
}
