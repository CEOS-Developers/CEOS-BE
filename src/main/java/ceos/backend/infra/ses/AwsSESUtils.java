package ceos.backend.infra.ses;


import ceos.backend.infra.ses.domain.EmailSendHistory;
import ceos.backend.infra.ses.domain.EmailType;
import ceos.backend.infra.ses.repository.EmailSendHistoryRepository;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSESUtils {
    private final SesAsyncClient sesAsyncClient;
    private final SpringTemplateEngine templateEngine;
    private final EmailSendHistoryRepository emailSendHistoryRepository;

    public void singleEmailRequest(
            String to, String subject, String template, Context context, EmailType emailType) {
        final String html = templateEngine.process(template, context);

        final SendEmailRequest.Builder sendEmailRequestBuilder = SendEmailRequest.builder();
        sendEmailRequestBuilder.destination(Destination.builder().toAddresses(to).build());

        SendEmailRequest request =
                sendEmailRequestBuilder
                        .message(newMessage(subject, html))
                        .source("ceos@ceos-sinchon.com")
                        .build();

        CompletableFuture<SendEmailResponse> future = sesAsyncClient.sendEmail(request);

        saveHistory(to, subject, template, emailType, future);
    }

    public void retryEmailRequest(EmailSendHistory history, Context context) {
        try {
            history.markRetryAttempt();
            emailSendHistoryRepository.save(history);

            final String html = templateEngine.process(history.getTemplateName(), context);
            SendEmailRequest request =
                    SendEmailRequest.builder()
                            .destination(
                                    Destination.builder()
                                            .toAddresses(history.getRecipientEmail())
                                            .build())
                            .message(newMessage(history.getSubject(), html))
                            .source("ceos@ceos-sinchon.com")
                            .build();

            CompletableFuture<SendEmailResponse> future = sesAsyncClient.sendEmail(request);
            updateRetryHistory(history.getId(), future);
        } catch (Exception e) {
            log.error(
                    "Failed to enqueue retry email. historyId={}, recipient={}",
                    history.getId(),
                    history.getRecipientEmail(),
                    e);
            updateRetryFailure(history.getId(), e);
        }
    }

    private Message newMessage(String subject, String html) {
        final Content content = Content.builder().data(subject).build();
        return Message.builder()
                .subject(content)
                .body(Body.builder().html(builder -> builder.data(html)).build())
                .build();
    }

    private void saveHistory(
            String to,
            String subject,
            String template,
            EmailType emailType,
            CompletableFuture<SendEmailResponse> future) {
        future.whenComplete(
                (response, exception) -> {
                    if (exception != null) {
                        log.error("Failed to send email to: {}", to, exception);
                        saveFailureHistory(to, subject, template, emailType, exception);
                    } else {
                        log.info(
                                "Successfully sent email to: {}, messageId: {}",
                                to,
                                response.messageId());
                        saveSuccessHistory(to, subject, template, emailType, response.messageId());
                    }
                });
    }

    private void saveSuccessHistory(
            String to, String subject, String template, EmailType emailType, String messageId) {
        try {
            EmailSendHistory history =
                    EmailSendHistory.createSuccess(to, subject, template, emailType, messageId);
            emailSendHistoryRepository.save(history);
        } catch (Exception e) {
            log.error("Failed to save email send success history", e);
        }
    }

    private void saveFailureHistory(
            String to, String subject, String template, EmailType emailType, Throwable exception) {
        try {
            EmailSendHistory history =
                    EmailSendHistory.createFailure(
                            to, subject, template, emailType, exception.getMessage());
            emailSendHistoryRepository.save(history);
        } catch (Exception e) {
            log.error("Failed to save email send failure history", e);
        }
    }

    private void updateRetryHistory(Long historyId, CompletableFuture<SendEmailResponse> future) {
        future.whenComplete(
                (response, exception) -> {
                    if (exception != null) {
                        log.error("Failed to retry email. historyId={}", historyId, exception);
                        updateRetryFailure(historyId, exception);
                    } else {
                        updateRetrySuccess(historyId, response.messageId());
                    }
                });
    }

    private void updateRetrySuccess(Long historyId, String messageId) {
        try {
            Optional<EmailSendHistory> optionalHistory =
                    emailSendHistoryRepository.findById(historyId);
            if (optionalHistory.isEmpty()) {
                log.warn("Retry success history not found. historyId={}", historyId);
                return;
            }

            EmailSendHistory history = optionalHistory.get();
            history.markSuccess(messageId);
            emailSendHistoryRepository.save(history);
            log.info(
                    "Successfully retried email. historyId={}, recipient={}, messageId={}",
                    historyId,
                    history.getRecipientEmail(),
                    messageId);
        } catch (Exception e) {
            log.error("Failed to update retry success history. historyId={}", historyId, e);
        }
    }

    private void updateRetryFailure(Long historyId, Throwable exception) {
        try {
            Optional<EmailSendHistory> optionalHistory =
                    emailSendHistoryRepository.findById(historyId);
            if (optionalHistory.isEmpty()) {
                log.warn("Retry failure history not found. historyId={}", historyId);
                return;
            }

            EmailSendHistory history = optionalHistory.get();
            history.markFailure(exception.getMessage());
            emailSendHistoryRepository.save(history);
        } catch (Exception e) {
            log.error("Failed to update retry failure history. historyId={}", historyId, e);
        }
    }
}
