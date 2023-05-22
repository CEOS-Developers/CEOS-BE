package ceos.backend.domain.application.mapper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.response.GetResultResponse;
import ceos.backend.domain.application.exception.InterviewNotFound;
import ceos.backend.domain.application.exception.QuestionNotFound;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewDateFormatter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class ApplicationMapper {
    public Application toEntity(CreateApplicationRequest request, String UUID) {
        return Application.of(request, UUID);
    }

    public List<ApplicationAnswer> toAnswerList(CreateApplicationRequest request,
                                                Application application,
                                                List<ApplicationQuestion> questions) {
        final Part part = request.getApplicationDetailVo().getPart();
        // common
        List<ApplicationAnswer> answers = new java.util.ArrayList<>(request.getCommonAnswers().stream()
                .map(answerVo -> {
                    ApplicationQuestion question = questions.stream()
                            .filter(q -> q.getCategory() == QuestionCategory.COMMON)
                            .filter(q -> Objects.equals(q.getId(), answerVo.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> QuestionNotFound.EXCEPTION);
                    return ApplicationAnswer.of(question, application, answerVo.getAnswer());
                }).toList());
        // part
        request.getPartAnswers()
                .forEach(answerVo -> {
                    ApplicationQuestion question = questions.stream()
                            .filter(q -> q.getCategory().toString().equals(part.toString()))
                            .filter(q -> Objects.equals(q.getId(), answerVo.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> QuestionNotFound.EXCEPTION);
                    answers.add(ApplicationAnswer.of(question, application, answerVo.getAnswer()));
                });
        return answers;
    }

    public List<ApplicationInterview> toInterviewList(List<String> unableTimes,
                                                      Application application,
                                                      List<Interview> interviews) {
        List<ApplicationInterview> applicationInterviews = interviews.stream()
                .filter(interview -> unableTimes
                        .contains(InterviewDateFormatter.interviewDateFormatter(interview)))
                .map(interview -> ApplicationInterview.of(application, interview))
                .toList();
        if (applicationInterviews.isEmpty()) {
            throw InterviewNotFound.EXCEPTION;
        }
        return applicationInterviews;
    }

    public GetResultResponse toGetResultResponse(Application application, boolean isDocument) {
        if (isDocument) {
            return GetResultResponse.toDocumentResult(application);
        }
        return GetResultResponse.toFinalResult(application);
    }
}
