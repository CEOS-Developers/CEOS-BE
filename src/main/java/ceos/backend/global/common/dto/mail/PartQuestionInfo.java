package ceos.backend.global.common.dto.mail;


import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PartQuestionInfo {
    private String part;
    private List<String> questions;
    private List<String> answers;

    @Builder
    private PartQuestionInfo(String part, List<String> questions, List<String> answers) {
        this.part = part;
        this.questions = questions;
        this.answers = answers;
    }

    public static PartQuestionInfo of(String part, List<String> questions, List<String> answers) {
        return PartQuestionInfo.builder().part(part).questions(questions).answers(answers).build();
    }
}
