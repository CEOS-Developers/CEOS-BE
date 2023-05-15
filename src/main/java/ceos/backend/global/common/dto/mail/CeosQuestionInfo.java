package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CeosQuestionInfo {
    private String otDate;
    private String demodayDate;
    private String otherActivities;

    @Builder
    private CeosQuestionInfo(String otDate, String demodayDate, String otherActivities) {
        this.otDate = otDate;
        this.demodayDate = demodayDate;
        this.otherActivities = otherActivities;
    }

    public static CeosQuestionInfo of(String otDate, String demodayDate, String otherActivities) {
        return CeosQuestionInfo.builder()
                .otDate(otDate)
                .demodayDate(demodayDate)
                .otherActivities(otherActivities)
                .build();
    }
}
