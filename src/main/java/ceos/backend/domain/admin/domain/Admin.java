package ceos.backend.domain.admin.domain;

import ceos.backend.global.common.entity.BaseEntity;
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
    private String part; // enum

    @NotNull
    @Size(max = 10)
    private String role; // enum

    @NotNull
    @Size(max = 255)
    private String email;
}
