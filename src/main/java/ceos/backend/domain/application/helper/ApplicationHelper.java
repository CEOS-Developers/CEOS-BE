package ceos.backend.domain.application.helper;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateAttendanceRequest;
import ceos.backend.domain.application.exception.exceptions.ApplicantNotFound;
import ceos.backend.domain.application.repository.ApplicationQuestionRepository;
import ceos.backend.domain.application.repository.ApplicationRepository;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import ceos.backend.global.common.event.Event;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationHelper {
    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;

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

    public void sendEmail(CreateApplicationRequest request, int generation, String UUID) {
        final List<ApplicationQuestion> applicationQuestions =
                applicationQuestionRepository.findAll();
        Event.raise(AwsSESMail.of(request, applicationQuestions, generation, UUID));
    }

    public void sendSlackUnableReasonMessage(
            Application application, UpdateAttendanceRequest request, boolean isfinal) {
        final SlackUnavailableReason reason =
                SlackUnavailableReason.of(application, request.getReason(), isfinal);
        Event.raise(reason);
    }

    public Application getApplicationById(Long id) {
        return applicationRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public Application getApplicationByIdForUpdate(Long id) {
        return applicationRepository
                .findByIdWithPessimisticLock(id)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public Application getApplicationByUuidAndEmail(String uuid, String email) {
        return applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public Application getApplicationByUuidAndEmailForUpdate(String uuid, String email) {
        return applicationRepository
                .findByUuidAndEmailWithPessimisticLock(uuid, email)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }
}
