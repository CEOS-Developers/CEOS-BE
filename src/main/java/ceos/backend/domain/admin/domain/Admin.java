package ceos.backend.domain.admin.domain;

import ceos.backend.domain.admin.dto.request.SignUpRequest;
import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import jakarta.annotation.Nullable;
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
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @NotNull
    @Size(max = 255)
    private String email;

    @Nullable
    @Size(max = 255)
    private String refreshToken;

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

    public static Admin of(SignUpRequest signUpRequest, String encodedPassword, int generation) {
        return Admin.builder()
                .username(signUpRequest.getAdminVo().getUsername())
                .password(encodedPassword)
                .generation(generation)
                .name(signUpRequest.getAdminVo().getName())
                .part(signUpRequest.getAdminVo().getPart())
                .role(AdminRole.ROLE_ANONYMOUS)
                .email(signUpRequest.getAdminVo().getEmail())
                .build();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateRandomPwd(String randomPwd) {
        this.password = randomPwd;
    }
    
    public void updatePwd(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public void updateRole(AdminRole adminRole) {
        this.role = adminRole;
    }
}
