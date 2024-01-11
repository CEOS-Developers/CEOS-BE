package ceos.backend.global.common.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsSESRecruitMail {
    //수정 예정 -> 이름을 받을 것인가 말 것인가...
    private String email;

    @Builder
    private AwsSESRecruitMail(String email) {
        this.email = email;
    }

    public static AwsSESRecruitMail from(String email) {
        return AwsSESRecruitMail.builder().email(email)
                .build();
    }
}
