package ceos.backend.global.common.dto.mail;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommonQuestionInfo {
    private List<String> questions;
    private List<String> answers;

    @Builder
    private CommonQuestionInfo(List<String> questions, List<String> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public static CommonQuestionInfo of(List<String> questions, List<String> answers) {
        return CommonQuestionInfo.builder()
                .questions(questions)
                .answers(answers)
                .build();
    }
}
