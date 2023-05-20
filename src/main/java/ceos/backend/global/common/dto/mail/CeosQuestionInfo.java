package ceos.backend.global.common.dto.mail;

import ceos.backend.domain.application.vo.ApplicationDetailVo;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

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

    public static CeosQuestionInfo from(ApplicationDetailVo applicationDetailVo) {
        return CeosQuestionInfo.builder()
                .otDate(applicationDetailVo.getOtDate().format(DateTimeFormatter.ISO_DATE))
                .demodayDate(applicationDetailVo.getDemodayDate().format(DateTimeFormatter.ISO_DATE))
                .otherActivities(applicationDetailVo.getOtherActivities())
                .build();
    }
}
