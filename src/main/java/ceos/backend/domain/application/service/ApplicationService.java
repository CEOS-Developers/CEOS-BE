package ceos.backend.domain.application.service;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateAttendanceRequest;
import ceos.backend.domain.application.dto.request.UpdateInterviewTime;
import ceos.backend.domain.application.dto.request.UpdatePassStatus;
import ceos.backend.domain.application.dto.response.GetResultResponse;
import ceos.backend.domain.application.helper.ApplicationHelper;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.global.common.dto.ParsedDuration;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import ceos.backend.global.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public GetResultResponse getDocumentResult(String uuid, String email) {
        // 서류 합격 기간 검증
        applicationHelper.validateDocumentResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessable(uuid, email);

        // dto
        return applicationMapper.toGetResultResponse(application, true);
    }

    @Transactional
    public void updateInterviewAttendance(String uuid, String email, UpdateAttendanceRequest request) {
        // 서류 합격 기간 검증
        applicationHelper.validateDocumentResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessable(uuid, email);

        // 유저 확인 여부 검증
        applicationHelper.validateApplicantInterviewCheckStatus(application);

        // 슬랙
        if (request.isAvailable()) {
            application.updateInterviewCheck(true);
            applicationRepository.save(application);
        } else {
            final SlackUnavailableReason reason =
                    SlackUnavailableReason.of(application, request.getReason(), false);
            Event.raise(reason);
        }
    }

    @Transactional(readOnly = true)
    public GetResultResponse getFinalResult(String uuid, String email) {
        // 최종 합격 기간 검증
        applicationHelper.validateFinalResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessable(uuid, email);

        // 유저 서류 합격 여부 검증
        applicationHelper.validateApplicantDocumentPass(application);

        // dto 생성
        return applicationMapper.toGetResultResponse(application, false);
    }

    @Transactional
    public void updateActivityAvailability(String uuid, String email, UpdateAttendanceRequest request) {
        // 최종 합격 기간 검증
        applicationHelper.validateFinalResultOption();

        // 유저 검증
        final Application application = applicationHelper.validateApplicantAccessable(uuid, email);

        // 유저 확인 여부 검증
        applicationHelper.validateApplicantActivityCheckStatus(application);

        // 슬랙
        if (request.isAvailable()) {
            application.updateFinalCheck(true);
            applicationRepository.save(application);
        } else {
            final SlackUnavailableReason reason
                    = SlackUnavailableReason.of(application, request.getReason(), true);
            Event.raise(reason);
        }
    }

    @Transactional
    public void updateInterviewTime(Long applicationId, UpdateInterviewTime updateInterviewTime) {
        // 기간 검증
        applicationHelper.validateDocumentPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // 서류 통과 검증
        applicationHelper.validateDocumentPassStatus(application);

        // 인터뷰 시간 검증
        final List<Interview> interviews = interviewRepository.findAll();
        applicationHelper.validateInterviewTime(interviews, updateInterviewTime.getInterviewTime());

         // status 변경
        application.updateInterviewTime(updateInterviewTime.getInterviewTime());
    }

    @Transactional
    public void updateDocumentPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        // 기간 검증
        applicationHelper.validateDocumentPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // status 변경
        application.updateDocumentPass(updatePassStatus.getPass());
    }

    @Transactional
    public void updateFinalPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        // 기간 검증
        applicationHelper.validateFinalPassDuration();

        // 유저 검증
        final Application application = applicationHelper.validateExistingApplicant(applicationId);

        // 서류 통과 검증
        applicationHelper.validateDocumentPassStatus(application);

        // status 변경
        application.updateFinalPass(updatePassStatus.getPass());
    }
}
