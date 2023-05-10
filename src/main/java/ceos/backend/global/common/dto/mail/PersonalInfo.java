package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalInfo {
    private String name;
    private String gender;
    private String birth;
    private String email;
    private String phone;

    @Builder
    public PersonalInfo(String name, String gender, String birth, String email, String phone) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
    }

    // TODO: 엔티티보고 재정의하기
    public static PersonalInfo of(String name, String gender, String birth, String email, String phone) {
        return PersonalInfo.builder()
                .name(name)
                .gender(gender)
                .birth(birth)
                .email(email)
                .phone(phone)
                .build();
    }
}
