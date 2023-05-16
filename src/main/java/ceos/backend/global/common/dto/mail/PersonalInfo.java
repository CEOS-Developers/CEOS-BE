package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalInfo {
    private String name;
    private String gender;
    private String birth;
    private String email;
    private String phoneNumber;

    @Builder
    private PersonalInfo(String name, String gender, String birth, String email, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static PersonalInfo of(String name, String gender, String birth, String email, String phoneNumber) {
        return PersonalInfo.builder()
                .name(name)
                .gender(gender)
                .birth(birth)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
