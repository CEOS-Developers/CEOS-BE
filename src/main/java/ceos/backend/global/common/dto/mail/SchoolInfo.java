package ceos.backend.global.common.dto.mail;

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

    public static SchoolInfo of(String university, String major, String semestersLeftNumber) {
        return SchoolInfo.builder()
                .university(university)
                .major(major)
                .semestersLeftNumber(semestersLeftNumber)
                .build();
    }
}
