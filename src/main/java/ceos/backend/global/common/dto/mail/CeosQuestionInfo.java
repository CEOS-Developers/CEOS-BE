package ceos.backend.global.common.dto.mail;


import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import java.time.format.DateTimeFormatter;
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

    public static CeosQuestionInfo from(CreateApplicationRequest request) {
        return CeosQuestionInfo.builder()
                .otDate(request.getOtDate().format(DateTimeFormatter.ISO_DATE))
                .demodayDate(request.getDemodayDate().format(DateTimeFormatter.ISO_DATE))
                .otherActivities(request.getOtherActivities())
                .build();
    }
}
