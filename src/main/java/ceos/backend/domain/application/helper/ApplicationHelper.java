package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.*;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.util.InterviewDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
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
                .findByEmail(applicantInfoVo.getEmail())
                .isPresent()) {
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
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        recruitment.validateGeneration(generation);
        LocalDate now = LocalDate.now();
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
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        LocalDate now = LocalDate.now();
        recruitment.validateDocumentResultDuration(now);
    }

    public void validateFinalResultOption() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        LocalDate now = LocalDate.now();
        recruitment.validateFinalResultDuration(now);
    }


    public void validateApplicantDocumentPass(Application application) {
        if (application.getDocumentPass() == Pass.FAIL) {
            throw NotPassDocument.EXCEPTION;
        }
    }

    public void validateApplicantInterviewCheckStatus(Application application) {
        if (application.getDocumentPass() == Pass.FAIL) {
            throw NotPassDocument.EXCEPTION;
        }
        if (application.isInterviewCheck()) {
            throw AlreadyCheckInterview.EXCEPTION;
        }
    }

    public void validateApplicantActivityCheckStatus(Application application) {
        if (application.getDocumentPass() != Pass.PASS) {
            throw NotPassDocument.EXCEPTION;
        }
        if (application.getFinalPass() != Pass.PASS) {
            throw NotPassFinal.EXCEPTION;
        }
        if (application.isFinalCheck()) {
            throw AlreadyCheckFinal.EXCEPTION;
        }
    }

    public void validateDocumentPassDuration() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        LocalDate now = LocalDate.now();
        recruitment.validateDocumentPassDuration(now);
    }

    public Application validateExistingApplicant(Long applicationId) {
        return applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> {
                    throw ApplicantNotFound.EXCEPTION;
                });
    }

    public void validateFinalPassDuration() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        LocalDate now = LocalDate.now();
        recruitment.validateFinalPassDuration(now);
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
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        LocalDate now = LocalDate.now();
        recruitment.validateBeforeStartDateDoc(now);
    }

    public void validateRemainApplications() {
        if (applicationAnswerRepository.count() != 0) {
            throw AnswerStillExist.EXCEPTION;
        }
        if (applicationInterviewRepository.count() != 0) {
            throw ApplicationInterviewStillExist.EXCEPTION;
        }
    }
}
