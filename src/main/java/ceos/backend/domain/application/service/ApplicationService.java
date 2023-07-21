package ceos.backend.domain.application.service;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.*;
import ceos.backend.domain.application.dto.response.*;
import ceos.backend.domain.application.enums.SortPartType;
import ceos.backend.domain.application.enums.SortPassType;
import ceos.backend.domain.application.helper.ApplicationHelper;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.validator.ApplicationValidator;
import ceos.backend.domain.application.vo.QuestionListVo;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.util.InterviewDateTimeConvertor;
import ceos.backend.global.util.ParsedDurationConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final ApplicationQuestionDetailRepository applicationQuestionDetailRepository;

    private final ApplicationMapper applicationMapper;
    private final ApplicationHelper applicationHelper;
    private final ApplicationValidator applicationValidator;

    private final RecruitmentHelper recruitmentHelper;


    @Transactional(readOnly = true)
    public GetApplications getApplications(int pageNum, int limit, SortPartType sortType,
                                           SortPassType docPass, SortPassType finalPass) {
        PageRequest pageRequest = PageRequest.of(pageNum, limit);
        Page<Application> pageManagements = applicationHelper
                .getApplications(docPass, finalPass, sortType, pageRequest);
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageManagements.getTotalPages(),
                pageManagements.getTotalElements());
        return applicationMapper.toGetApplications(pageManagements, pageInfo);
    }

    @Transactional
    public void createApplication(CreateApplicationRequest createApplicationRequest) {
        applicationValidator.validateBetweenStartDateDocAndEndDateDoc(); // 제출 기간
        applicationValidator.validateFirstApplication(createApplicationRequest.getApplicantInfoVo()); // 중복 검사
        applicationValidator.validateQAMatching(createApplicationRequest); // 질문 다 채웠나 검사

        final String UUID = applicationHelper.generateUUID();
        final int generation = recruitmentHelper.takeRecruitment().getGeneration();
        final Application application = applicationMapper.toEntity(createApplicationRequest, generation, UUID);

        final List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findAll();
        final List<ApplicationAnswer> applicationAnswers
                = applicationMapper.toAnswerList(createApplicationRequest, application, applicationQuestions);
        applicationAnswerRepository.saveAll(applicationAnswers);

        final List<String> unableTimes = InterviewDateTimeConvertor
                .toStringDuration(createApplicationRequest.getUnableTimes());
        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationMapper.toApplicationInterviewList(unableTimes, application, interviews);
        applicationInterviewRepository.saveAll(applicationInterviews);

        application.addApplicationAnswerList(applicationAnswers);
        application.addApplicationInterviewList(applicationInterviews);
        applicationRepository.save(application);

        // 이메일 전송
        applicationHelper.sendEmail(createApplicationRequest, generation, UUID);
    }

    @Transactional(readOnly = true)
    public GetApplicationQuestion getApplicationQuestion() {
        // dto
        final List<ApplicationQuestion> applicationQuestions
                = applicationQuestionRepository.findAll();
        final List<ApplicationQuestionDetail> applicationQuestionDetails
                = applicationQuestionDetailRepository.findAll();
        final List<Interview> interviews = interviewRepository.findAll();
        return applicationMapper.toGetApplicationQuestion(applicationQuestions,
                applicationQuestionDetails, interviews);
    }

    @Transactional
    public void updateApplicationQuestion(UpdateApplicationQuestion updateApplicationQuestion) {
        applicationValidator.validateBeforeStartDateDoc(); // 기간 확인
        applicationValidator.validateRemainApplications(); // 남은 응답 확인

        applicationQuestionRepository.deleteAll();
        applicationQuestionDetailRepository.deleteAll();
        interviewRepository.deleteAll();

        final QuestionListVo questionListVo = applicationMapper.toQuestionList(updateApplicationQuestion);
        applicationQuestionRepository.saveAll(questionListVo.getApplicationQuestions());
        applicationQuestionDetailRepository.saveAll(questionListVo.getApplicationQuestionDetails());

        List<String> times = InterviewDateTimeConvertor.toStringDuration(updateApplicationQuestion.getTimes());
        final List<Interview> interviews = applicationMapper.toInterviewList(times);
        interviewRepository.saveAll(interviews);
    }

    @Transactional(readOnly = true)
    public GetResultResponse getDocumentResult(String uuid, String email) {
        applicationValidator.validateBetweenResultDateDocAndResultDateFinal(); // 서류 합격 기간 검증
        applicationValidator.validateApplicantAccessible(uuid, email); // 유저 검증

        final Application application = applicationHelper.getApplicationByUuidAndEmail(uuid, email);
        return applicationMapper.toGetResultResponse(application, true);
    }

    @Transactional
    public void updateInterviewAttendance(String uuid, String email, UpdateAttendanceRequest request) {
        applicationValidator.validateBetweenResultDateDocAndResultDateFinal(); // 서류 합격 기간 검증
        applicationValidator.validateApplicantAccessible(uuid, email); // 유저 검증
        final Application application = applicationHelper.getApplicationByUuidAndEmail(uuid, email);
        applicationValidator.validateApplicantInterviewCheckStatus(application); // 서류합격, 인터뷰 체크 검증

        if (request.isAvailable()) {
            application.updateInterviewCheck(true);
            applicationRepository.save(application);
        } else {
            applicationHelper.sendSlackUnableReasonMessage(application, request, false);
        }
    }

    @Transactional(readOnly = true)
    public GetResultResponse getFinalResult(String uuid, String email) {
        applicationValidator.validateFinalResultAbleDuration(); // 최종 합격 기간 검증
        applicationValidator.validateApplicantAccessible(uuid, email); // 유저 검증
        final Application application = applicationHelper.getApplicationByUuidAndEmail(uuid, email);
        applicationValidator.validateApplicantDocumentPass(application); // 유저 서류 합격 여부 검증

        return applicationMapper.toGetResultResponse(application, false);
    }

    @Transactional
    public void updateParticipationAvailability(String uuid, String email, UpdateAttendanceRequest request) {
        applicationValidator.validateFinalResultAbleDuration(); // 최종 합격 기간 검증
        applicationValidator.validateApplicantAccessible(uuid, email); // 유저 검증
        final Application application = applicationHelper.getApplicationByUuidAndEmail(uuid, email);
        applicationValidator.validateApplicantActivityCheckStatus(application); // 유저 확인 여부 검증

        if (request.isAvailable()) {
            application.updateFinalCheck(true);
            applicationRepository.save(application);
        } else {
            applicationHelper.sendSlackUnableReasonMessage(application, request, true);
        }
    }

    @Transactional(readOnly = true)
    public GetApplication getApplication(Long applicationId) {
        applicationValidator.validateExistingApplicant(applicationId); // 유저 검증

        final Application application = applicationHelper.getApplicationById(applicationId);
        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationInterviewRepository.findAllByApplication(application);
        final List<ApplicationQuestion> applicationQuestions
                = applicationQuestionRepository.findAll();
        final List<ApplicationQuestionDetail> applicationQuestionDetails
                = applicationQuestionDetailRepository.findAll();
        final List<ApplicationAnswer> applicationAnswers
                = applicationAnswerRepository.findAllByApplication(application);
        return applicationMapper.toGetApplication(application, interviews, applicationInterviews,
                applicationQuestions, applicationQuestionDetails, applicationAnswers);
    }

    @Transactional(readOnly = true)
    public GetInterviewTime getInterviewTime(Long applicationId) {
        applicationValidator.validateExistingApplicant(applicationId); // 유저 검증
        final Application application = applicationHelper.getApplicationById(applicationId);
        applicationValidator.validateDocumentPassStatus(application); // 서류 통과 검증

        final List<Interview> interviews = interviewRepository.findAll();
        final List<ApplicationInterview> applicationInterviews
                = applicationInterviewRepository.findAllByApplication(application);
        return applicationMapper.toGetInterviewTime(interviews, applicationInterviews);
    }

    @Transactional
    public void updateInterviewTime(Long applicationId, UpdateInterviewTime updateInterviewTime) {
        applicationValidator.validateDocumentPassDuration(); // 기간 검증
        applicationValidator.validateExistingApplicant(applicationId); // 유저 검증
        final Application application = applicationHelper.getApplicationById(applicationId);
        applicationValidator.validateDocumentPassStatus(application);  // 서류 통과 검증
        final List<Interview> interviews = interviewRepository.findAll();
        final String duration = ParsedDurationConvertor.toStringDuration(updateInterviewTime.getParsedDuration());
        applicationValidator.validateInterviewTime(interviews, duration); // 인터뷰 시간 검증

         // status 변경
        application.updateInterviewTime(duration);
    }

    @Transactional
    public void updateDocumentPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        applicationValidator.validateDocumentPassDuration(); // 기간 검증
        applicationValidator.validateExistingApplicant(applicationId); // 유저 검증

        final Application application = applicationHelper.getApplicationById(applicationId);
        application.updateDocumentPass(updatePassStatus.getPass());
    }

    @Transactional
    public void updateFinalPassStatus(Long applicationId, UpdatePassStatus updatePassStatus) {
        applicationValidator.validateFinalPassDuration(); // 기간 검증
        applicationValidator.validateExistingApplicant(applicationId); // 유저 검증
        final Application application = applicationHelper.getApplicationById(applicationId);
        applicationValidator.validateDocumentPassStatus(application); // 서류 통과 검증

        application.updateFinalPass(updatePassStatus.getPass());
    }
}
