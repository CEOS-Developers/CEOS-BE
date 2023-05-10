package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SchoolInfo {
    private String schoolName;
    private String major;
    private String remainSemester;

    @Builder
    public SchoolInfo(String schoolName, String major, String remainSemester) {
        this.schoolName = schoolName;
        this.major = major;
        this.remainSemester = remainSemester;
    }

    // TODO: 엔티티보고 재정의하기
    public static SchoolInfo of(String schoolName, String major, String remainSemester) {
        return SchoolInfo.builder()
                .schoolName(schoolName)
                .major(major)
                .remainSemester(remainSemester)
                .build();
    }
}
