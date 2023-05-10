package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PartQuestionInfo {
    private String part;
    private List<String> questions;
    private List<String> answers;

    @Builder
    public PartQuestionInfo(String part, List<String> questions, List<String> answers) {
        this.part = part;
        this.questions = questions;
        this.answers = answers;
    }

    // TODO: 엔티티보고 재정의하기
    public static PartQuestionInfo of(String part, List<String> questions, List<String> answers) {
        return PartQuestionInfo.builder()
                .part(part)
                .questions(questions)
                .answers(answers)
                .build();
    }
}
