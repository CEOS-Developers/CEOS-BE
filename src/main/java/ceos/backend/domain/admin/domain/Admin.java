package ceos.backend.domain.admin.domain;

import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 128)
    private String password;

    @NotNull
    private int generation;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @NotNull
    @Size(max = 255)
    private String email;

    // 생성자
    @Builder
    private Admin(String username,
                  String password,
                  int generation,
                  String name,
                  Part part,
                  AdminRole role,
                  String email) {

        this.username = username;
        this.password = password;
        this.generation = generation;
        this.name = name;
        this.part = part;
        this.role = role;
        this.email = email;
    }

    // 정적 팩토리 메서드
}
