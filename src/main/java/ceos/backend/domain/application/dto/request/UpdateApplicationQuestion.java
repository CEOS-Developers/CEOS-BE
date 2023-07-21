package ceos.backend.domain.application.dto.request;


import ceos.backend.domain.application.vo.InterviewDateTimesVo;
import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.global.common.annotation.ValidQuestionOrder;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdateApplicationQuestion {
    @ValidQuestionOrder @Valid private List<QuestionVo> commonQuestions;

    @ValidQuestionOrder @Valid private List<QuestionVo> productQuestions;

    @ValidQuestionOrder @Valid private List<QuestionVo> designQuestions;

    @ValidQuestionOrder @Valid private List<QuestionVo> frontendQuestions;

    @ValidQuestionOrder @Valid private List<QuestionVo> backendQuestions;

    @Valid private List<InterviewDateTimesVo> times;
}
