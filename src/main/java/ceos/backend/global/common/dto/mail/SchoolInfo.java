package ceos.backend.global.common.dto.mail;


import ceos.backend.domain.application.vo.ApplicantInfoVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SchoolInfo {
    private String university;
    private String major;
    private String semestersLeftNumber;

    @Builder
    private SchoolInfo(String university, String major, String semestersLeftNumber) {
        this.university = university;
        this.major = major;
        this.semestersLeftNumber = semestersLeftNumber;
    }

    public static SchoolInfo from(ApplicantInfoVo applicantInfoVo) {
        return SchoolInfo.builder()
                .university(applicantInfoVo.getUniversity().toString())
                .major(applicantInfoVo.getMajor())
                .semestersLeftNumber(Integer.toString(applicantInfoVo.getSemestersLeftNumber()))
                .build();
    }
}
