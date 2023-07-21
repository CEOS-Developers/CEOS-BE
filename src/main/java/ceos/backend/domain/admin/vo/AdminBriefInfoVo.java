package ceos.backend.domain.admin.vo;


import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.domain.AdminRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminBriefInfoVo {

    private Long id;
    private String name;
    private String email;
    private AdminRole adminRole;

    @Builder
    private AdminBriefInfoVo(Long id, String name, String email, AdminRole adminRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.adminRole = adminRole;
    }

    public static AdminBriefInfoVo from(Admin admin) {
        return AdminBriefInfoVo.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .adminRole(admin.getRole())
                .build();
    }
}
