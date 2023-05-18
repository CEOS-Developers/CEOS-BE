package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.DuplicateApplicant;
import ceos.backend.domain.application.exception.NotApplicationDuration;
import ceos.backend.domain.application.exception.WrongGeneration;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.settings.domain.Settings;
import ceos.backend.domain.settings.helper.SettingsHelper;
import ceos.backend.domain.settings.repository.SettingsRepository;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        Event.raise(AwsSESMail.from(request, applicationQuestions, UUID));
    }

    public void validateRecruitOption(int generation) {
        Settings settings = settingsHelper.takeSetting();

        if (settings.getGeneration() != generation) {
            throw WrongGeneration.EXCEPTION;
        }

        LocalDate now = LocalDate.now();
        if (now.compareTo(settings.getStartDateDoc()) < 0) {
            throw NotApplicationDuration.EXCEPTION;
        }
        if (now.isAfter(settings.getEndDateDoc())) {
            throw NotApplicationDuration.EXCEPTION;
        }

    }
}
