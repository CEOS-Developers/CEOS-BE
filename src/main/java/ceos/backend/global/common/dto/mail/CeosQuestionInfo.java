package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CeosQuestionInfo {
    private String ot;
    private String demo;
    private String otherActivities;

    @Builder
    public CeosQuestionInfo(String ot, String demo, String otherActivities) {
        this.ot = ot;
        this.demo = demo;
        this.otherActivities = otherActivities;
    }


    // TODO: 엔티티보고 재정의하기
    public static CeosQuestionInfo of(String ot, String demo, String otherActivities) {
        return CeosQuestionInfo.builder()
                .ot(ot)
                .demo(demo)
                .otherActivities(otherActivities)
                .build();
    }
}
