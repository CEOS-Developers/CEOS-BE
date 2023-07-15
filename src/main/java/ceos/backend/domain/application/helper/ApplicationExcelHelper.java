package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.ApplicationInterview;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.Interview;
import ceos.backend.domain.application.repository.InterviewRepository;
import ceos.backend.global.util.InterviewDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplicationExcelHelper {

    private final InterviewRepository interviewRepository;

    public Map<Long, Integer> getQuestionIndexMap(List<String> headers, List<ApplicationQuestion> questionList) {
        Map<Long, Integer> questionIndexMap = new HashMap<>();
        int colIndex = headers.size();
        for (ApplicationQuestion applicationQuestion : questionList) {
            headers.add(applicationQuestion.getQuestion());
            questionIndexMap.put(applicationQuestion.getId(), colIndex++); // Map : 정렬된 질문 id, index
        }
        return questionIndexMap;
    }

    public Map<Long, String> getInterviewTimeMap() {
        Map<Long, String> interviewTimeMap = new HashMap<>();

        List<Interview> interviewList = interviewRepository.findAll();

        for (Interview interview : interviewList) {
            String duration = InterviewDateFormatter.interviewDateFormatter(interview);
            interviewTimeMap.put(interview.getId(), duration); // Map : 면접 시간 id, 면접 시간 format
        }
        return interviewTimeMap;
    }
    public String getPossibleInterview(Map<Long, String> interviewTimeMap, List<ApplicationInterview> applicationInterviews) {
        String possibleInterview = "";
        for (ApplicationInterview interview : applicationInterviews) {
            possibleInterview += interviewTimeMap.get(interview.getInterview().getId()) + "\n";
        }
        return possibleInterview;
    }
}
