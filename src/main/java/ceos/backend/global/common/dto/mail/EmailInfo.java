package ceos.backend.global.common.dto.mail;


import ceos.backend.global.common.dto.AwsSESRecruitMail;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailInfo {
    private String email;

    @Builder
    private EmailInfo(String email) {
        this.email = email;
    }

    public static EmailInfo from(AwsSESRecruitMail awsSESRecruitMail) {
        return EmailInfo.builder().email(awsSESRecruitMail.getEmail()).build();
    }
}
