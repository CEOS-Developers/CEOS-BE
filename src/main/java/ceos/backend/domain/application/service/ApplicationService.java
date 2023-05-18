package ceos.backend.domain.application.service;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.helper.ApplicationHelper;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final InterviewRepository interviewRepository;
    private final ApplicationInterviewRepository applicationInterviewRepository;

    private final ApplicationMapper applicationMapper;
    private final ApplicationHelper applicationHelper;

    @Transactional
    public void createApplication(CreateApplicationRequest createApplicationRequest) {
        // 제출 기간, 기수 검사
        applicationHelper.validateRecruitOption(createApplicationRequest.getApplicationDetailVo().getGeneration());

        // 중복 검사
        applicationHelper.validateFirstApplication(createApplicationRequest.getApplicantInfoVo());

        // 엔티티 생성 및 저장
        final String UUID = applicationHelper.generateUUID();
        final Application application = applicationMapper.toEntity(createApplicationRequest, UUID);
        applicationRepository.save(application);

        final List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findAll();
        final List<ApplicationAnswer> applicationAnswers
                = applicationMapper.toAnswerList(createApplicationRequest, application, applicationQuestions);
        applicationAnswerRepository.saveAll(applicationAnswers);

        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationMapper.toInterviewList(createApplicationRequest.getUnableTimes(),
                                                    application, interviews);
        applicationInterviewRepository.saveAll(applicationInterviews);

        // 이메일 전송
        applicationHelper.sendEmail(createApplicationRequest, UUID);
    }
}
