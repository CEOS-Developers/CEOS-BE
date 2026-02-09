package ceos.backend.infra.ses;


import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.QuestionCategory;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.exceptions.QuestionNotFound;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.AwsSESPasswordMail;
import ceos.backend.global.common.dto.AwsSESRecruitMail;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.dto.mail.*;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewDateTimeConvertor;
import ceos.backend.global.util.ParsedDurationConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AwsSESMailGenerator {

    private final RecruitmentHelper recruitmentHelper;

    public Context generateApplicationMailContext(AwsSESMail awsSESMail) {
        final CreateApplicationRequest request = awsSESMail.getCreateApplicationRequest();
        final List<ApplicationQuestion> questions = awsSESMail.getApplicationQuestions();
        final String UUID = awsSESMail.getUUID();

        List<String> commonQ = new ArrayList<>(), commonA = new ArrayList<>();
        questions.stream()
                .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                .sorted(Comparator.comparing(ApplicationQuestion::getNumber))
                .forEach(
                        question -> {
                            commonQ.add(generateQuestion(question));
                            commonA.add(generateAnswer(request.getCommonAnswers(), question));
                        });

        List<String> partQ = new ArrayList<>(), partA = new ArrayList<>();
        final Part part = request.getPart();
        questions.stream()
                .filter(question -> question.getCategory().toString().equals(part.toString()))
                .sorted(Comparator.comparing(ApplicationQuestion::getNumber))
                .forEach(
                        question -> {
                            partQ.add(generateQuestion(question));
                            partA.add(generateAnswer(request.getPartAnswers(), question));
                        });

        final List<String> unableTimes =
                InterviewDateTimeConvertor.toStringDuration(request.getUnableTimes());
        List<ParsedDuration> parsedDurations =
                unableTimes.stream()
                        .map(ParsedDurationConvertor::parsingDuration)
                        .sorted(Comparator.comparing(ParsedDuration::getDuration))
                        .sorted(Comparator.comparing(ParsedDuration::getDate))
                        .toList();

        List<String> dates =
                parsedDurations.stream()
                        .map(ParsedDuration::getDate)
                        .distinct()
                        .collect(Collectors.toList());

        List<List<String>> times = new ArrayList<>();
        dates.forEach(
                date ->
                        times.add(
                                parsedDurations.stream()
                                        .filter(
                                                parsedDuration ->
                                                        Objects.equals(
                                                                parsedDuration.getDate(), date))
                                        .map(ParsedDuration::getDuration)
                                        .collect(Collectors.toList())));

        Context context = new Context();
        context.setVariable("greetInfo", GreetInfo.of(request, awsSESMail.getGeneration()));
        context.setVariable("uuidInfo", UuidInfo.of(request.getApplicantInfoVo(), UUID));
        context.setVariable("personalInfo", PersonalInfo.from(request.getApplicantInfoVo()));
        context.setVariable("schoolInfo", SchoolInfo.from(request.getApplicantInfoVo()));
        context.setVariable("ceosQuestionInfo", CeosQuestionInfo.from(request));
        context.setVariable("commonQuestionInfo", CommonQuestionInfo.of(commonQ, commonA));
        context.setVariable(
                "partQuestionInfo", PartQuestionInfo.of(request.getPart().getPart(), partQ, partA));
        context.setVariable("interviewDateInfo", InterviewDateInfo.of(times, dates));

        return context;
    }

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

        context.setVariable(
                "startDateDoc", recruitment.getStartDateDoc().format(dateFormatter));
        context.setVariable("endDateDoc", recruitment.getEndDateDoc().format(dateTimeFormatter));
        context.setVariable(
                "resultDateDoc", recruitment.getResultDateDoc().format(dateFormatter));
        context.setVariable(
                "startDateInterview", recruitment.getStartDateInterview().format(dateFormatter));
        context.setVariable(
                "endDateInterview", recruitment.getEndDateInterview().format(dateFormatter));
        context.setVariable(
                "resultDateFinal", recruitment.getResultDateFinal().format(dateFormatter));
    }
}
