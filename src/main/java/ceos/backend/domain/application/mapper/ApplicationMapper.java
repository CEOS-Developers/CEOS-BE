package ceos.backend.domain.application.mapper;

import static java.util.Map.*;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateApplicationQuestion;
import ceos.backend.domain.application.dto.response.*;
import ceos.backend.domain.application.exception.exceptions.InterviewNotFound;
import ceos.backend.domain.application.exception.exceptions.QuestionNotFound;
import ceos.backend.domain.application.vo.*;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewConvertor;
import ceos.backend.global.util.ParsedDurationConvertor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {
    public Application toEntity(CreateApplicationRequest request, int generation, String UUID) {
        return Application.of(request, generation, UUID);
    }

    public List<ApplicationAnswer> toAnswerList(
            CreateApplicationRequest request,
            Application application,
            List<ApplicationQuestion> questions) {
        final Part part = request.getPart();
        // common
        List<ApplicationAnswer> answers =
                new java.util.ArrayList<>(
                        request.getCommonAnswers().stream()
                                .map(
                                        answerVo ->
                                                toApplicationAnswer(
                                                        questions,
                                                        application,
                                                        answerVo,
                                                        part,
                                                        true))
                                .toList());
        // part
        request.getPartAnswers()
                .forEach(
                        answerVo -> {
                            answers.add(
                                    toApplicationAnswer(
                                            questions, application, answerVo, part, false));
                        });
        return answers;
    }

    private ApplicationAnswer toApplicationAnswer(
            List<ApplicationQuestion> questions,
            Application application,
            AnswerVo answerVo,
            Part part,
            boolean isCommon) {
        final String category = isCommon ? "COMMON" : part.toString();

        ApplicationQuestion question =
                questions.stream()
                        .filter(q -> q.getCategory().toString().equals(category))
                        .filter(q -> Objects.equals(q.getId(), answerVo.getQuestionId()))
                        .findFirst()
                        .orElseThrow(() -> QuestionNotFound.EXCEPTION);
        return ApplicationAnswer.of(question, application, answerVo.getAnswer());
    }

    public List<ApplicationInterview> toApplicationInterviewList(
            List<String> unableTimes, Application application, List<Interview> interviews) {
        List<ApplicationInterview> applicationInterviews =
                interviews.stream()
                        .filter(
                                interview ->
                                        unableTimes.contains(
                                                InterviewConvertor.interviewDateFormatter(
                                                        interview)))
                        .map(interview -> ApplicationInterview.of(application, interview))
                        .toList();
        if (applicationInterviews.isEmpty() && !unableTimes.isEmpty()) {
            throw InterviewNotFound.EXCEPTION;
        }
        return applicationInterviews;
    }

    public GetResultResponse toGetResultResponse(
            Application application, Recruitment recruitment, boolean isDocument) {
        if (isDocument) {
            return GetResultResponse.toDocumentResult(application, recruitment);
        }
        return GetResultResponse.toFinalResult(application, recruitment);
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
        parsingQuestion(questions, questionDetails, productQuestions, QuestionCategory.PRODUCT);
        parsingQuestion(questions, questionDetails, designQuestions, QuestionCategory.DESIGN);
        parsingQuestion(questions, questionDetails, frontendQuestions, QuestionCategory.FRONTEND);
        parsingQuestion(questions, questionDetails, backendQuestions, QuestionCategory.BACKEND);
        return QuestionListVo.of(questions, questionDetails);
    }

    private void parsingQuestion(
            List<ApplicationQuestion> questions,
            List<ApplicationQuestionDetail> questionDetails,
            List<QuestionVo> ansQuestions,
            QuestionCategory category) {
        ansQuestions.forEach(
                questionVo -> {
                    final ApplicationQuestion applicationQuestion =
                            ApplicationQuestion.of(questionVo, category);
                    questions.add(applicationQuestion);
                    questionVo
                            .getQuestionDetail()
                            .forEach(
                                    questionDetailVo -> {
                                        questionDetails.add(
                                                ApplicationQuestionDetail.of(
                                                        applicationQuestion, questionDetailVo));
                                    });
                });
    }

    public List<Interview> toInterviewList(List<String> times) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        List<Interview> interviews = new ArrayList<>();
        times.forEach(
                time -> {
                    final String[] strTimes = time.split(" - ");
                    final LocalDateTime fromTime = LocalDateTime.parse(strTimes[0], formatter);
                    final LocalDateTime toTime = LocalDateTime.parse(strTimes[1], formatter);
                    interviews.add(Interview.of(fromTime, toTime));
                });
        return interviews;
    }

    public GetInterviewTime toGetInterviewTime(
            List<Interview> interviews, List<ApplicationInterview> applicationInterviews) {
        return GetInterviewTime.builder()
                .times(toInterviewTimeVoList(interviews, applicationInterviews))
                .build();
    }

    public GetApplicationQuestion toGetApplicationQuestion(
            List<ApplicationQuestion> applicationQuestions,
            List<ApplicationQuestionDetail> applicationQuestionDetails,
            List<Interview> interviews) {
        List<QuestionWithIdVo> commonQuestions = new ArrayList<>();
        List<QuestionWithIdVo> productQuestions = new ArrayList<>();
        List<QuestionWithIdVo> frontendQuestions = new ArrayList<>();
        List<QuestionWithIdVo> backendQuestions = new ArrayList<>();
        List<QuestionWithIdVo> designQuestions = new ArrayList<>();
        applicationQuestions.forEach(
                applicationQuestion -> {
                    final List<QuestionDetailVo> questionDetailVos =
                            applicationQuestionDetails.stream()
                                    .filter(
                                            details ->
                                                    details.getApplicationQuestion()
                                                            .equals(applicationQuestion))
                                    .map(QuestionDetailVo::from)
                                    .toList();
                    final QuestionWithIdVo questionVo =
                            QuestionWithIdVo.of(applicationQuestion, questionDetailVos);
                    switch (applicationQuestion.getCategory()) {
                        case COMMON -> commonQuestions.add(questionVo);
                        case PRODUCT -> productQuestions.add(questionVo);
                        case DESIGN -> designQuestions.add(questionVo);
                        case FRONTEND -> frontendQuestions.add(questionVo);
                        case BACKEND -> backendQuestions.add(questionVo);
                    }
                });

        final List<ParsedDuration> parsedDurations =
                interviews.stream()
                        .map(InterviewConvertor::interviewDateFormatter)
                        .map(ParsedDurationConvertor::parsingYearDuration)
                        .toList();
        final Set<String> dateSets =
                parsedDurations.stream().map(ParsedDuration::getDate).collect(Collectors.toSet());

        final Comparator<InterviewDateTimesVo> order =
                Comparator.comparing(InterviewDateTimesVo::getDate);

        final List<InterviewDateTimesVo> interviewDateTimesVos =
                dateSets.stream()
                        .map(
                                dateSet ->
                                        InterviewDateTimesVo.of(
                                                dateSet,
                                                parsedDurations.stream()
                                                        .filter(
                                                                parsedDuration ->
                                                                        parsedDuration
                                                                                .getDate()
                                                                                .equals(dateSet))
                                                        .map(ParsedDuration::getDuration)
                                                        .toList()))
                        .sorted(order)
                        .toList();

        return GetApplicationQuestion.of(
                commonQuestions,
                productQuestions,
                designQuestions,
                frontendQuestions,
                backendQuestions,
                interviewDateTimesVos);
    }

    public GetApplication toGetApplication(
            Application application,
            List<Interview> interviews,
            List<ApplicationInterview> applicationInterviews,
            List<ApplicationQuestion> applicationQuestions,
            List<ApplicationQuestionDetail> applicationQuestionDetails,
            List<ApplicationAnswer> applicationAnswers) {
        // qna common
        final List<QnAVo> commonQuestions =
                applicationQuestions.stream()
                        .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                        .map(
                                question ->
                                        toQnAVo(
                                                applicationAnswers,
                                                applicationQuestionDetails,
                                                question))
                        .toList();
        // qna part
        final Part part = application.getApplicationDetail().getPart();
        final List<QnAVo> partQuestions =
                applicationQuestions.stream()
                        .filter(
                                question ->
                                        question.getCategory().toString().equals(part.toString()))
                        .map(
                                question ->
                                        toQnAVo(
                                                applicationAnswers,
                                                applicationQuestionDetails,
                                                question))
                        .toList();

        // interview
        List<InterviewTimeVo> times = toInterviewTimeVoList(interviews, applicationInterviews);
        return GetApplication.of(application, commonQuestions, partQuestions, times);
    }

    private QnAVo toQnAVo(
            List<ApplicationAnswer> applicationAnswers,
            List<ApplicationQuestionDetail> applicationQuestionDetails,
            ApplicationQuestion question) {
        return applicationAnswers.stream()
                .filter(
                        applicationAnswer ->
                                applicationAnswer.getApplicationQuestion().equals(question))
                .map(
                        answer -> {
                            final List<QuestionDetailVo> detailVos =
                                    applicationQuestionDetails.stream()
                                            .filter(
                                                    details ->
                                                            details.getApplicationQuestion()
                                                                    .equals(question))
                                            .map(QuestionDetailVo::from)
                                            .toList();
                            return QnAVo.of(question, detailVos, answer);
                        })
                .findFirst()
                .orElseThrow();
    }

    private List<InterviewTimeVo> toInterviewTimeVoList(
            List<Interview> interviews, List<ApplicationInterview> applicationInterviews) {
        return interviews.stream()
                .map(
                        interview -> {
                            final String duration =
                                    InterviewConvertor.interviewDateFormatter(interview);
                            final ParsedDuration parsedDuration =
                                    ParsedDurationConvertor.parsingYearDuration(duration);
                            if (applicationInterviews.stream()
                                    .anyMatch(
                                            applicationInterview ->
                                                    applicationInterview
                                                            .getInterview()
                                                            .equals(interview))) {
                                return InterviewTimeVo.of(true, parsedDuration);
                            }
                            return InterviewTimeVo.of(false, parsedDuration);
                        })
                .toList();
    }

    public GetApplications toGetApplications(Page<Application> pageManagements, PageInfo pageInfo) {
        List<ApplicationBriefInfoVo> applicationBriefInfoVos =
                pageManagements.stream()
                        .map(
                                vo ->
                                        ApplicationBriefInfoVo.of(
                                                vo, toParsedDuration(vo.getInterviewDatetime())))
                        .toList();
        return GetApplications.of(applicationBriefInfoVos, pageInfo);
    }

    private ParsedDuration toParsedDuration(String time) {
        if (time == null) {
            return ParsedDuration.toNullParsedDuration();
        }
        return ParsedDurationConvertor.parsingYearDuration(time);
    }
}
