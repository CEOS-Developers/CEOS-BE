package ceos.backend.infra.ses;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationAnswer;
import ceos.backend.domain.application.domain.ApplicationInterview;
import ceos.backend.domain.application.repository.ApplicationAnswerRepository;
import ceos.backend.domain.application.repository.ApplicationInterviewRepository;
import ceos.backend.domain.application.repository.ApplicationRepository;
import ceos.backend.infra.ses.domain.EmailSendHistory;
import ceos.backend.infra.ses.domain.EmailType;
import ceos.backend.infra.ses.domain.SendStatus;
import ceos.backend.infra.ses.repository.EmailSendHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEmailRetryScheduler {
    private final EmailSendHistoryRepository emailSendHistoryRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;
    private final ApplicationInterviewRepository applicationInterviewRepository;
    private final AwsSESMailGenerator awsSESMailGenerator;
    private final AwsSESUtils awsSESUtils;

    @Value("${aws.ses.retry.max-count:5}")
    private int maxRetryCount;

    @Scheduled(fixedDelayString = "${aws.ses.retry.fixed-delay-ms:60000}")
    public void retryFailedApplicationEmails() {
        final List<EmailSendHistory> failures =
                emailSendHistoryRepository.findRetryTargets(
                        SendStatus.FAILURE,
                        EmailType.APPLICATION,
                        maxRetryCount,
                        PageRequest.of(0, 20));

        if (failures.isEmpty()) {
            return;
        }

        for (EmailSendHistory history : failures) {
            try {
                final Application application =
                        applicationRepository
                                .findByEmail(history.getRecipientEmail())
                                .orElseThrow(
                                        () ->
                                                new IllegalStateException(
                                                        "application not found by email"));
                final List<ApplicationAnswer> answers =
                        applicationAnswerRepository.findAllByApplication(application);
                final List<ApplicationInterview> interviews =
                        applicationInterviewRepository.findAllByApplication(application);
                final Context context =
                        awsSESMailGenerator.generateApplicationMailContext(
                                application, answers, interviews);

                awsSESUtils.retryEmailRequest(history, context);
            } catch (Exception e) {
                history.markRetryAttempt();
                history.markFailure(e.getMessage());
                emailSendHistoryRepository.save(history);
                log.error(
                        "Failed to retry application email. historyId={}, recipient={}",
                        history.getId(),
                        history.getRecipientEmail(),
                        e);
            }
        }
    }
}
