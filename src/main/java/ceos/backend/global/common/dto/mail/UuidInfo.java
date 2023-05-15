package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UuidInfo {
    private String name;
    private String uuid;

    @Builder
    private UuidInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public static UuidInfo of(String name, String uuid) {
        return UuidInfo.builder()
                .uuid(uuid)
                .name(name)
                .build();
    }
}
