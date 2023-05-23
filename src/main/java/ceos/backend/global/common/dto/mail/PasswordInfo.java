package ceos.backend.global.common.dto.mail;

import ceos.backend.global.common.dto.AwsSESPasswordMail;
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

    public static PasswordInfo from(AwsSESPasswordMail awsSESPasswordMail) {
        return PasswordInfo.builder()
                .email(awsSESPasswordMail.getEmail())
                .name(awsSESPasswordMail.getName())
                .randomPwd(awsSESPasswordMail.getRandomPwd())
                .build();
    }
}
