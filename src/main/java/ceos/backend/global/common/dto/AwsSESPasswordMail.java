package ceos.backend.global.common.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsSESPasswordMail {
    private String email;
    private String name;
    private String randomPwd;

    @Builder
    private AwsSESPasswordMail(String email, String name, String randomPwd) {
        this.email = email;
        this.name = name;
        this.randomPwd = randomPwd;
    }

    public static AwsSESPasswordMail of(String email, String name, String randomPwd) {
        return AwsSESPasswordMail.builder().email(email).name(name).randomPwd(randomPwd).build();
    }
}
