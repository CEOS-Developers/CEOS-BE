package ceos.backend.infra.ses;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.QuestionCategory;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.dto.mail.*;
import ceos.backend.global.util.ParsingDuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsSESMailGenerator {

    public Context generateApplicationMailContext(AwsSESMail awsSESMail) {
        final CreateApplicationRequest request = awsSESMail.getCreateApplicationRequest();
        final List<ApplicationQuestion> questions = awsSESMail.getApplicationQuestions();
        final String UUID = awsSESMail.getUUID();

        List<String> commonQ = new ArrayList<>(), commonA = new ArrayList<>();
        questions.stream()
                .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                .sorted(Comparator.comparing(ApplicationQuestion::getNumber))
                .forEach(question -> {
                    commonQ.add(generateQuestion(question));
                    commonA.add(generateAnswer(request.getCommonAnswers(), question));
                });

        List<String> partQ = new ArrayList<>(), partA = new ArrayList<>();
        questions.stream()
                .filter(question -> question.getCategory()
                        .validateCategory(request.getApplicationDetailVo().getPart().toString()))
                .sorted(Comparator.comparing(ApplicationQuestion::getNumber))
                .forEach(question -> {
                    partQ.add(generateQuestion(question));
                    partA.add(generateAnswer(request.getCommonAnswers(), question));
                });

        List<ParsedDuration> parsedDurations = request.getUnableTimes().stream()
                .map(ParsingDuration::parsingDuration)
                .sorted(Comparator.comparing(ParsedDuration::getDuration))
                .sorted(Comparator.comparing(ParsedDuration::getDate))
                .toList();

        List<String> dates = parsedDurations.stream()
                .map(ParsedDuration::getDate)
                .distinct()
                .collect(Collectors.toList());

        List<List<String>> times = new ArrayList<>();
        dates.forEach(date -> times.add(parsedDurations.stream()
                        .filter(parsedDuration -> Objects.equals(parsedDuration.getDate(), date))
                        .map(ParsedDuration::getDuration)
                        .collect(Collectors.toList())));


        Context context = new Context();
        context.setVariable("greetInfo", GreetInfo.from(request));
        context.setVariable("uuidInfo", UuidInfo.of(request.getApplicantInfoVo(),UUID));
        context.setVariable("personalInfo", PersonalInfo.from(request.getApplicantInfoVo()));
        context.setVariable("schoolInfo", SchoolInfo.from(request.getApplicantInfoVo()));
        context.setVariable("ceosQuestionInfo", CeosQuestionInfo.from(request.getApplicationDetailVo()));
        context.setVariable("commonQuestionInfo", CommonQuestionInfo.of(commonQ, commonA));
        context.setVariable("partQuestionInfo",
                PartQuestionInfo.of(request.getApplicationDetailVo().getPart().getPart(), partQ, partA));
        context.setVariable("interviewDateInfo", InterviewDateInfo.of(times, dates));

        return context;
    }

    private String generateQuestion(ApplicationQuestion applicationQuestion) {
        return applicationQuestion.getNumber() + " : " + applicationQuestion.getQuestion();
    }

    private String generateAnswer(List<AnswerVo> answerVos, ApplicationQuestion applicationQuestion) {
        // 이전에 이미 예외처리 했기 때문에 이렇게 진행해도 문제 없음
        final AnswerVo ans = answerVos.stream()
                .filter(answerVo -> Objects.equals(applicationQuestion.getId(), answerVo.getQuestionId()))
                .findFirst()
                .orElseThrow();
        return ans.getAnswer();
    }

    public String generateApplicationMailSubject(int generation) {
        return "세오스 "+ Integer.toString(generation) +"기 지원 알림드립니다.";
    }
}
