package ceos.backend.global.common.dto.mail;

import ceos.backend.domain.admin.domain.Admin;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordInfo {
    private String name;
    private String uuid;

    @Builder
    private PasswordInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public static PasswordInfo of(Admin admin, String UUID) {
        return PasswordInfo.builder()
                .name(admin.getName())
                .uuid(UUID)
                .build();
    }
}
