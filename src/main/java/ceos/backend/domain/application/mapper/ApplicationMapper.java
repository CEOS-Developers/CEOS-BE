package ceos.backend.domain.application.mapper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateApplicationQuestion;
import ceos.backend.domain.application.dto.response.GetResultResponse;
import ceos.backend.domain.application.exception.InterviewNotFound;
import ceos.backend.domain.application.exception.QuestionNotFound;
import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.global.common.annotation.ValidDateList;
import ceos.backend.global.common.annotation.ValidTimeDuration;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.DateTimeConvertor;
import ceos.backend.global.util.InterviewDateFormatter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<ApplicationInterview> toApplicationInterviewList(List<String> unableTimes,
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
    
    public List<ApplicationQuestion> toQuestionList(UpdateApplicationQuestion updateApplicationQuestion) {
        final List<QuestionVo> commonQuestions = updateApplicationQuestion.getCommonQuestions();
        final List<QuestionVo> productQuestions = updateApplicationQuestion.getProductQuestions();
        final List<QuestionVo> designQuestions = updateApplicationQuestion.getDesignQuestions();
        final List<QuestionVo> frontendQuestions = updateApplicationQuestion.getFrontendQuestions();
        final List<QuestionVo> backendQuestions = updateApplicationQuestion.getBackendQuestions();

        List<ApplicationQuestion> questions = new ArrayList<>();
        commonQuestions.forEach(questionVo -> {
                    questions.add(ApplicationQuestion.of(questionVo, QuestionCategory.COMMON));
                });
        productQuestions.forEach(questionVo -> {
            questions.add(ApplicationQuestion.of(questionVo, QuestionCategory.PRODUCT));
        });
        designQuestions.forEach(questionVo -> {
            questions.add(ApplicationQuestion.of(questionVo, QuestionCategory.DESIGN));
        });
        frontendQuestions.forEach(questionVo -> {
            questions.add(ApplicationQuestion.of(questionVo, QuestionCategory.FRONTEND));
        });
        backendQuestions.forEach(questionVo -> {
            questions.add(ApplicationQuestion.of(questionVo, QuestionCategory.BACKEND));
        });
        return questions;
    }

    public List<Interview> toInterviewList(UpdateApplicationQuestion updateApplicationQuestion) {
        final List<String> dates = updateApplicationQuestion.getDates();
        final List<String> times = updateApplicationQuestion.getTimes();

        List<Interview> interviews = new ArrayList<>();
        dates.forEach(date -> {
            times.forEach(time -> {
                final String[] strTimes = time.split(" - ");
                final LocalDateTime fromTime = DateTimeConvertor.dateTimeConvertor(date, strTimes[0]);
                final LocalDateTime toTime = DateTimeConvertor.dateTimeConvertor(date, strTimes[1]);
                interviews.add(Interview.of(fromTime, toTime));
            });
        });
        return interviews;
    }
}
