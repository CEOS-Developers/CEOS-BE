package ceos.backend.global.common.dto.mail;


import ceos.backend.domain.application.vo.ApplicantInfoVo;
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
    private PersonalInfo(
            String name, String gender, String birth, String email, String phoneNumber) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static PersonalInfo from(ApplicantInfoVo applicantInfoVo) {
        return PersonalInfo.builder()
                .name(applicantInfoVo.getName())
                .gender(applicantInfoVo.getGender().toString())
                .birth(applicantInfoVo.getBirth().toString())
                .email(applicantInfoVo.getEmail())
                .phoneNumber(applicantInfoVo.getPhoneNumber())
                .build();
    }
}
