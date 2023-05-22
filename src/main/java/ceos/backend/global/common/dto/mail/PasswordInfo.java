package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordInfo {
    private String email;
    private String name;
    private String randomPwd;

    @Builder
    private PasswordInfo(String email, String name, String randomPwd) {
        this.email = email;
        this.name = name;
        this.randomPwd = randomPwd;
    }

    public static PasswordInfo of(String email, String name, String randomPwd) {
        return PasswordInfo.builder()
                .email(email)
                .name(name)
                .randomPwd(randomPwd)
                .build();
    }
}
