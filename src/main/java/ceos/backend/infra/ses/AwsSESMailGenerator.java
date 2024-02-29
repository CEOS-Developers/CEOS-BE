package ceos.backend.infra.ses;


import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.QuestionCategory;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.exceptions.QuestionNotFound;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.AwsSESPasswordMail;
import ceos.backend.global.common.dto.AwsSESRecruitMail;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.dto.mail.*;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewDateTimeConvertor;
import ceos.backend.global.util.ParsedDurationConvertor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class AwsSESMailGenerator {

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
        Context context = new Context();
        context.setVariable("email", EmailInfo.from(awsSESRecruitMail));

        return context;
    }

    // 수정 예정
    public String generateRecruitMailSubject() {
        return "세오스 리쿠르팅 메일 발송";
    }
}
