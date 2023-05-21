package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.Pass;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.*;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.settings.domain.Settings;
import ceos.backend.domain.settings.helper.SettingsHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.event.Event;
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
    private final SettingsHelper settingsHelper;

    public void validateFirstApplication(ApplicantInfoVo applicantInfoVo) {
        if (applicationRepository
                .findByNameAndPhoneNumber(applicantInfoVo.getName(), applicantInfoVo.getPhoneNumber())
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
        Settings settings = settingsHelper.takeSetting();
        settings.validateGeneration(generation);
        LocalDate now = LocalDate.now();
        settings.validateApplyDuration(now);
    }

    public Application validateApplicantAccessable(String uuid, String email) {
        return applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(() -> {
                    throw ApplicantNotFound.EXCEPTION;
                });
    }

    public void validateDocumentResultOption() {
        Settings settings = settingsHelper.takeSetting();
        LocalDate now = LocalDate.now();
        settings.validateDocumentResultDuration(now);
    }

    public void validateFinalResultOption() {
        Settings settings = settingsHelper.takeSetting();
        LocalDate now = LocalDate.now();
        settings.validateFinalResultDuration(now);
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
        Settings settings = settingsHelper.takeSetting();
        LocalDate now = LocalDate.now();
        settings.validateDocumentPassDuration(now);
    }

    public Application validateExistingApplicant(Long applicationId) {
        return applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> {
                    throw ApplicantNotFound.EXCEPTION;
                });
    }

    public void validateFinalPassDuration() {
        Settings settings = settingsHelper.takeSetting();
        LocalDate now = LocalDate.now();
        settings.validateFinalPassDuration(now);
    }

    public void validateDocumentPassStatus(Application application) {
        application.validateDocumentPass();
    }
}
