package ceos.backend.domain.application.mapper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateApplicationQuestion;
import ceos.backend.domain.application.dto.response.*;
import ceos.backend.domain.application.exception.InterviewNotFound;
import ceos.backend.domain.application.exception.QuestionNotFound;
import ceos.backend.domain.application.vo.*;
import ceos.backend.global.common.annotation.ValidDateList;
import ceos.backend.global.common.annotation.ValidTimeDuration;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.DateTimeConvertor;
import ceos.backend.global.util.InterviewDateFormatter;
import ceos.backend.global.util.ParsingDuration;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    
    public QuestionListVo toQuestionList(UpdateApplicationQuestion updateApplicationQuestion) {
        final List<QuestionVo> commonQuestions = updateApplicationQuestion.getCommonQuestions();
        final List<QuestionVo> productQuestions = updateApplicationQuestion.getProductQuestions();
        final List<QuestionVo> designQuestions = updateApplicationQuestion.getDesignQuestions();
        final List<QuestionVo> frontendQuestions = updateApplicationQuestion.getFrontendQuestions();
        final List<QuestionVo> backendQuestions = updateApplicationQuestion.getBackendQuestions();

        List<ApplicationQuestion> questions = new ArrayList<>();
        List<ApplicationQuestionDetail> questionDetails = new ArrayList<>();
        parsingQuestion(questions, questionDetails, commonQuestions, QuestionCategory.COMMON);
        parsingQuestion(questions, questionDetails, productQuestions, QuestionCategory.STRATEGY);
        parsingQuestion(questions, questionDetails, designQuestions, QuestionCategory.DESIGN);
        parsingQuestion(questions, questionDetails, frontendQuestions, QuestionCategory.FRONTEND);
        parsingQuestion(questions, questionDetails, backendQuestions, QuestionCategory.BACKEND);
        return QuestionListVo.of(questions, questionDetails);
    }

    private void parsingQuestion(List<ApplicationQuestion> questions,
                                 List<ApplicationQuestionDetail> questionDetails,
                                 List<QuestionVo> ansQuestions,
                                 QuestionCategory category) {
        ansQuestions.forEach(questionVo -> {
            final ApplicationQuestion applicationQuestion
                    = ApplicationQuestion.of(questionVo, category);
            questions.add(applicationQuestion);
            questionVo.getQuestionDetail().forEach(questionDetailVo -> {
                questionDetails.add(ApplicationQuestionDetail.of(applicationQuestion, questionDetailVo));
            });
        });
    }

    public List<Interview> toInterviewList(List<String> times) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        List<Interview> interviews = new ArrayList<>();
        times.forEach(time -> {
            final String[] strTimes = time.split(" - ");
            final LocalDateTime fromTime = LocalDateTime.parse(strTimes[0], formatter);
            final LocalDateTime toTime = LocalDateTime.parse(strTimes[1], formatter);
            interviews.add(Interview.of(fromTime, toTime));
        });
        return interviews;
    }

    public GetInterviewTime toGetInterviewTime(List<Interview> interviews,
                                               List<ApplicationInterview> applicationInterviews) {
        return GetInterviewTime.builder()
                .times(toInterviewTimeVoList(interviews, applicationInterviews))
                .build();
    }

    public GetApplicationQuestion toGetApplicationQuestion(List<ApplicationQuestion> applicationQuestions,
                                                           List<ApplicationQuestionDetail> applicationQuestionDetails,
                                                           List<Interview> interviews) {
        List<QuestionVo> commonQuestions = new ArrayList<>();
        List<QuestionVo> productQuestions = new ArrayList<>();
        List<QuestionVo> frontendQuestions = new ArrayList<>();
        List<QuestionVo> backendQuestions = new ArrayList<>();
        List<QuestionVo> designQuestions = new ArrayList<>();
        applicationQuestions.forEach(applicationQuestion -> {
            final List<QuestionDetailVo> questionDetailVos = applicationQuestionDetails.stream()
                    .filter(details -> details.getApplicationQuestion().equals(applicationQuestion))
                    .map(QuestionDetailVo::from)
                    .toList();
            final QuestionVo questionVo = QuestionVo.of(applicationQuestion, questionDetailVos);
            switch (applicationQuestion.getCategory()) {
                case COMMON -> commonQuestions.add(questionVo);
                case STRATEGY -> productQuestions.add(questionVo);
                case DESIGN -> designQuestions.add(questionVo);
                case FRONTEND -> frontendQuestions.add(questionVo);
                case BACKEND -> backendQuestions.add(questionVo);
            }
        });

        final List<String> times = interviews.stream()
                .map(InterviewDateFormatter::interviewDateFormatter)
                .toList();
        return GetApplicationQuestion.of(commonQuestions, productQuestions, designQuestions,
                frontendQuestions, backendQuestions, times);
    }

    public GetApplication toGetApplication(Application application, List<Interview> interviews,
                                           List<ApplicationInterview> applicationInterviews,
                                           List<ApplicationQuestion> applicationQuestions,
                                           List<ApplicationAnswer> applicationAnswers) {
        // qna common
        final List<QnAVo> commonQuestions = applicationQuestions.stream()
                .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                .map(question ->
                    applicationAnswers.stream()
                            .filter(applicationAnswer -> applicationAnswer.getApplicationQuestion()
                                    .equals(question))
                            .map(answer -> QnAVo.of(question, answer))
                            .findFirst()
                            .orElseThrow()
                ).toList();
        // qna part
        final Part part = application.getApplicationDetail().getPart();
        final List<QnAVo> partQuestions = applicationQuestions.stream()
                .filter(question -> question.getCategory().toString().equals(part.toString()))
                .map(question ->
                        applicationAnswers.stream()
                                .filter(applicationAnswer -> applicationAnswer.getApplicationQuestion()
                                        .equals(question))
                                .map(answer -> QnAVo.of(question, answer))
                                .findFirst()
                                .orElseThrow()
                ).toList();

        // interview
        List<InterviewTimeVo> times = toInterviewTimeVoList(interviews, applicationInterviews);
        return GetApplication.of(application, commonQuestions, partQuestions, times);
    }

    private List<InterviewTimeVo> toInterviewTimeVoList(List<Interview> interviews, List<ApplicationInterview> applicationInterviews) {
        List<InterviewTimeVo> times = interviews.stream()
                .map(interview -> {
                    final String duration = InterviewDateFormatter.interviewDateFormatter(interview);
                    final ParsedDuration parsedDuration = ParsingDuration.parsingDuration(duration);
                    if (applicationInterviews.stream()
                            .anyMatch(applicationInterview -> applicationInterview.getInterview().equals(interview))) {
                        return InterviewTimeVo.of(true, parsedDuration);
                    }
                    return InterviewTimeVo.of(false, parsedDuration);
                })
                .toList();
        return times;
    }

    public GetApplications toGetApplications(Page<Application> pageManagements, PageInfo pageInfo) {
        List<ApplicationBriefInfoVo> applicationBriefInfoVos = pageManagements.stream()
                .map(ApplicationBriefInfoVo::from)
                .toList();
        return GetApplications.of(applicationBriefInfoVos, pageInfo);
    }
}
