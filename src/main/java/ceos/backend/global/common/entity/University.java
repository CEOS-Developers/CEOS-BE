package ceos.backend.global.common.entity;

public enum University {
    SOGANG("서강대학교"),
    YONSEI("연세대학교"),
    EWHA("이화여자대학교"),
    HONGIK("홍익대학교");

    private String university;

    // 생성자 (싱글톤)
    private University(String university) {
        this.university = university;
    }

    // Getter
    public String getUniversity() {
        return university;
    }
}
