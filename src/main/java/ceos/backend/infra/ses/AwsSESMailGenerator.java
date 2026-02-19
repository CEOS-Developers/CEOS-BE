package ceos.backend.infra.ses;


import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.exceptions.QuestionNotFound;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.AwsSESPasswordMail;
import ceos.backend.global.common.dto.AwsSESRecruitMail;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.dto.mail.*;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewConvertor;
import ceos.backend.global.util.InterviewDateTimeConvertor;
import ceos.backend.global.util.ParsedDurationConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AwsSESMailGenerator {

    private final RecruitmentHelper recruitmentHelper;

    public Context generateApplicationMailContext(AwsSESMail awsSESMail) {
        final CreateApplicationRequest request = awsSESMail.getCreateApplicationRequest();
        final Part part = request.getPart();
        final MailQuestionSection questionSection =
                buildQuestionSectionFromRequest(
                        awsSESMail.getApplicationQuestions(),
                        request.getCommonAnswers(),
                        request.getPartAnswers(),
                        part);

        return createApplicationMailContext(
                GreetInfo.of(request, awsSESMail.getGeneration()),
                UuidInfo.of(request.getApplicantInfoVo(), awsSESMail.getUUID()),
                PersonalInfo.from(request.getApplicantInfoVo()),
                SchoolInfo.from(request.getApplicantInfoVo()),
                CeosQuestionInfo.from(request),
                CommonQuestionInfo.of(
                        questionSection.commonQuestions(), questionSection.commonAnswers()),
                PartQuestionInfo.of(
                        part.getPart(),
                        questionSection.partQuestions(),
                        questionSection.partAnswers()),
                buildInterviewDateInfo(
                        InterviewDateTimeConvertor.toStringDuration(request.getUnableTimes())));
    }

    public Context generateApplicationMailContext(
            Application application,
            List<ApplicationAnswer> applicationAnswers,
            List<ApplicationInterview> applicationInterviews) {

        final Part part = application.getApplicationDetail().getPart();
        final MailQuestionSection questionSection =
                buildQuestionSectionFromSavedAnswers(applicationAnswers, part);
        final List<String> unableTimes =
                applicationInterviews.stream()
                        .map(ApplicationInterview::getInterview)
                        .filter(Objects::nonNull)
                        .map(InterviewConvertor::interviewDateFormatter)
                        .toList();

        final ApplicantInfoVo applicantInfoVo = ApplicantInfoVo.from(application);

        return createApplicationMailContext(
                GreetInfo.builder()
                        .name(applicantInfoVo.getName())
                        .generation(
                                Integer.toString(
                                        application.getApplicationDetail().getGeneration()))
                        .build(),
                UuidInfo.of(applicantInfoVo, application.getApplicantInfo().getUuid()),
                PersonalInfo.from(applicantInfoVo),
                SchoolInfo.from(applicantInfoVo),
                CeosQuestionInfo.builder()
                        .otDate(
                                application
                                        .getApplicationDetail()
                                        .getOtDate()
                                        .format(DateTimeFormatter.ISO_DATE))
                        .demodayDate(
                                application
                                        .getApplicationDetail()
                                        .getDemodayDate()
                                        .format(DateTimeFormatter.ISO_DATE))
                        .otherActivities(application.getApplicationDetail().getOtherActivities())
                        .build(),
                CommonQuestionInfo.of(
                        questionSection.commonQuestions(), questionSection.commonAnswers()),
                PartQuestionInfo.of(
                        part.getPart(),
                        questionSection.partQuestions(),
                        questionSection.partAnswers()),
                buildInterviewDateInfo(unableTimes));
    }

    private MailQuestionSection buildQuestionSectionFromRequest(
            List<ApplicationQuestion> questions,
            List<AnswerVo> commonAnswers,
            List<AnswerVo> partAnswers,
            Part part) {
        List<String> commonQ = new ArrayList<>();
        List<String> commonA = new ArrayList<>();
        List<String> partQ = new ArrayList<>();
        List<String> partA = new ArrayList<>();

        questions.stream()
                .sorted(Comparator.comparingInt(ApplicationQuestion::getNumber))
                .forEach(
                        question -> {
                            if (isCommonQuestion(question)) {
                                commonQ.add(generateQuestion(question));
                                commonA.add(generateAnswer(commonAnswers, question));
                                return;
                            }
                            if (isPartQuestion(question, part)) {
                                partQ.add(generateQuestion(question));
                                partA.add(generateAnswer(partAnswers, question));
                            }
                        });

        return new MailQuestionSection(commonQ, commonA, partQ, partA);
    }

    private MailQuestionSection buildQuestionSectionFromSavedAnswers(
            List<ApplicationAnswer> applicationAnswers, Part part) {
        List<String> commonQ = new ArrayList<>();
        List<String> commonA = new ArrayList<>();
        List<String> partQ = new ArrayList<>();
        List<String> partA = new ArrayList<>();

        applicationAnswers.stream()
                .filter(answer -> answer.getApplicationQuestion() != null)
                .sorted(
                        Comparator.comparingInt(
                                answer -> answer.getApplicationQuestion().getNumber()))
                .forEach(
                        answer -> {
                            final ApplicationQuestion question = answer.getApplicationQuestion();
                            if (isCommonQuestion(question)) {
                                commonQ.add(generateQuestion(question));
                                commonA.add(answer.getAnswer());
                                return;
                            }
                            if (isPartQuestion(question, part)) {
                                partQ.add(generateQuestion(question));
                                partA.add(answer.getAnswer());
                            }
                        });

        return new MailQuestionSection(commonQ, commonA, partQ, partA);
    }

    private InterviewDateInfo buildInterviewDateInfo(List<String> unableTimes) {
        List<ParsedDuration> parsedDurations =
                unableTimes.stream()
                        .map(ParsedDurationConvertor::parsingDuration)
                        .sorted(
                                Comparator.comparing(ParsedDuration::getDate)
                                        .thenComparing(ParsedDuration::getDuration))
                        .toList();

        List<String> dates =
                parsedDurations.stream().map(ParsedDuration::getDate).distinct().toList();
        List<List<String>> times =
                dates.stream()
                        .map(
                                date ->
                                        parsedDurations.stream()
                                                .filter(
                                                        parsedDuration ->
                                                                Objects.equals(
                                                                        parsedDuration.getDate(),
                                                                        date))
                                                .map(ParsedDuration::getDuration)
                                                .toList())
                        .toList();

        return InterviewDateInfo.of(times, dates);
    }

    private Context createApplicationMailContext(
            GreetInfo greetInfo,
            UuidInfo uuidInfo,
            PersonalInfo personalInfo,
            SchoolInfo schoolInfo,
            CeosQuestionInfo ceosQuestionInfo,
            CommonQuestionInfo commonQuestionInfo,
            PartQuestionInfo partQuestionInfo,
            InterviewDateInfo interviewDateInfo) {
        Context context = new Context();
        context.setVariable("greetInfo", greetInfo);
        context.setVariable("uuidInfo", uuidInfo);
        context.setVariable("personalInfo", personalInfo);
        context.setVariable("schoolInfo", schoolInfo);
        context.setVariable("ceosQuestionInfo", ceosQuestionInfo);
        context.setVariable("commonQuestionInfo", commonQuestionInfo);
        context.setVariable("partQuestionInfo", partQuestionInfo);
        context.setVariable("interviewDateInfo", interviewDateInfo);
        return context;
    }

    private boolean isCommonQuestion(ApplicationQuestion question) {
        return question.getCategory() == QuestionCategory.COMMON;
    }

    private boolean isPartQuestion(ApplicationQuestion question, Part part) {
        return question.getCategory().name().equals(part.name());
    }

    private record MailQuestionSection(
            List<String> commonQuestions,
            List<String> commonAnswers,
            List<String> partQuestions,
            List<String> partAnswers) {}

    private String generateQuestion(ApplicationQuestion applicationQuestion) {
        return applicationQuestion.getNumber() + " : " + applicationQuestion.getQuestion();
    }

    private String generateAnswer(
            List<AnswerVo> answerVos, ApplicationQuestion applicationQuestion) {
        final AnswerVo ans =
                answerVos.stream()
                        .filter(
                                answerVo ->
                                        applicationQuestion
                                                .getId()
                                                .equals(answerVo.getQuestionId()))
                        .findFirst()
                        .orElseThrow(
                                () -> {
                                    throw QuestionNotFound.EXCEPTION;
                                });
        return ans.getAnswer();
    }

    public String generateApplicationMailSubject(int generation) {
        return "세오스 " + Integer.toString(generation) + "기 지원 알림드립니다.";
    }

    public Context generatePasswordMailContext(AwsSESPasswordMail awsSESPasswordMail) {
        Context context = new Context();
        context.setVariable("passwordInfo", PasswordInfo.from(awsSESPasswordMail));

        return context;
    }

    public String generatePasswordMailSubject() {
        return "세오스 관리자 페이지 임시 비밀번호 발급";
    }

    public Context generateRecruitMailContext(AwsSESRecruitMail awsSESRecruitMail) {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        Context context = new Context();
        context.setVariable("email", EmailInfo.from(awsSESRecruitMail));
        context.setVariable("generation", recruitment.getGeneration());

        addRecruitDateToContext(context, recruitment);

        return context;
    }

    public String generateRecruitMailSubject() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        return "[CEOS] 세오스 " + recruitment.getGeneration() + "기 리크루팅을 시작합니다!";
    }

    private void addRecruitDateToContext(Context context, Recruitment recruitment) {
        // 모집 일정 포맷팅 (한국어 요일)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN);
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("M월 d일 (E) HH:mm", Locale.KOREAN);

        context.setVariable("startDateDoc", recruitment.getStartDateDoc().format(dateFormatter));
        context.setVariable("endDateDoc", recruitment.getEndDateDoc().format(dateTimeFormatter));
        context.setVariable("resultDateDoc", recruitment.getResultDateDoc().format(dateFormatter));
        context.setVariable(
                "startDateInterview", recruitment.getStartDateInterview().format(dateFormatter));
        context.setVariable(
                "endDateInterview", recruitment.getEndDateInterview().format(dateFormatter));
        context.setVariable(
                "resultDateFinal", recruitment.getResultDateFinal().format(dateFormatter));
    }
}
