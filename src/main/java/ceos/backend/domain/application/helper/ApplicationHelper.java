package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.*;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.util.InterviewDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationHelper {
    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final ApplicationInterviewRepository applicationInterviewRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;
    private final RecruitmentHelper recruitmentHelper;

    public void validateFirstApplication(ApplicantInfoVo applicantInfoVo) {
        if (applicationRepository
                .existsByEmail(applicantInfoVo.getEmail())) {
            throw DuplicateApplicant.EXCEPTION;
        }
    }

    public String generateUUID() {
        String newUUID;
        while (true) {
            newUUID = UUID.randomUUID().toString();
            if (applicationRepository.findByUuid(newUUID).isEmpty()) {
                break;
            }
        }
        return newUUID;
    }

    public void sendEmail(CreateApplicationRequest request, String UUID) {
        final List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findAll();
        Event.raise(AwsSESMail.of(request, applicationQuestions, UUID));
    }

    public void validateRecruitOption(int generation) {
        final LocalDate now = LocalDate.now();
        final Recruitment recruitment = recruitmentHelper.takeRecruitment();
        recruitment.validateGeneration(generation);
        recruitment.validateApplyDuration(now);
    }

    public Application validateApplicantAccessible(String uuid, String email) {
        return applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(() -> {
                    throw ApplicantNotFound.EXCEPTION;
                });
    }

    public void validateDocumentResultOption() {
        final LocalDate now = LocalDate.now();
        recruitmentHelper.takeRecruitment()
                .validateDocumentResultDuration(now);
    }

    public void validateFinalResultOption() {
        LocalDate now = LocalDate.now();
        recruitmentHelper.takeRecruitment()
                .validateFinalResultDuration(now);
    }


    public void validateApplicantDocumentPass(Application application) {
        application.validateDocumentPass();
    }

    public void validateApplicantInterviewCheckStatus(Application application) {
        application.validateDocumentPass();
        application.validateNotInterviewCheck();
    }

    public void validateApplicantActivityCheckStatus(Application application) {
        application.validateDocumentPass();
        application.validateFinalPass();
        application.validateNotFinalCheck();
    }

    public void validateDocumentPassDuration() {
        LocalDate now = LocalDate.now();
        recruitmentHelper.takeRecruitment()
                .validateDocumentPassDuration(now);
    }

    public Application validateExistingApplicant(Long applicationId) {
        return applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> {
                    throw ApplicantNotFound.EXCEPTION;
                });
    }

    public void validateFinalPassDuration() {
        LocalDate now = LocalDate.now();
        recruitmentHelper.takeRecruitment()
                .validateFinalPassDuration(now);
    }

    public void validateDocumentPassStatus(Application application) {
        application.validateDocumentPass();
    }

    public void validateInterviewTime(List<Interview> interviews, String interviewTime) {
        if(interviews.stream()
                .noneMatch(interview -> interviewTime
                        .equals(InterviewDateFormatter.interviewDateFormatter(interview)))) {
            throw InterviewNotFound.EXCEPTION;
        }
    }

    public void validateBeforeStartDateDoc() {
        LocalDate now = LocalDate.now();
        recruitmentHelper.takeRecruitment()
                .validateBeforeStartDateDoc(now);
    }

    public void validateRemainApplications() {
        if (applicationAnswerRepository.count() != 0) {
            throw AnswerStillExist.EXCEPTION;
        }
        if (applicationInterviewRepository.count() != 0) {
            throw ApplicationInterviewStillExist.EXCEPTION;
        }
    }

    public void validateQAMatching(List<ApplicationQuestion> applicationQuestions, CreateApplicationRequest createApplicationRequest) {
        if (applicationQuestions.stream()
                .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                .count() != createApplicationRequest.getCommonAnswers().size()) {
            throw NotMatchingQnA.EXCEPTION;
        }
        final Part part = createApplicationRequest.getApplicationDetailVo().getPart();
        if (applicationQuestions.stream()
                .filter(question -> question.getCategory().toString().equals(part.toString()))
                .count() != createApplicationRequest.getPartAnswers().size()) {
            throw NotMatchingQnA.EXCEPTION;
        }
    }
}
