package ceos.backend.infra.ses.domain;


import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "email_send_history")
public class EmailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "template_name")
    private String templateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_type")
    private EmailType emailType;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_status")
    private SendStatus sendStatus;

    @Column(name = "message_id", length = 255)
    private String messageId;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "last_retried_at")
    private LocalDateTime lastRetriedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private EmailSendHistory(
            String recipientEmail,
            String subject,
            String templateName,
            EmailType emailType,
            SendStatus sendStatus,
            String messageId,
            String errorMessage,
            Integer retryCount,
            LocalDateTime lastRetriedAt) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.templateName = templateName;
        this.emailType = emailType;
        this.sendStatus = sendStatus;
        this.messageId = messageId;
        this.errorMessage = errorMessage;
        this.retryCount = retryCount;
        this.lastRetriedAt = lastRetriedAt;
    }

    public static EmailSendHistory createSuccess(
            String recipientEmail,
            String subject,
            String templateName,
            EmailType emailType,
            String messageId) {
        return EmailSendHistory.builder()
                .recipientEmail(recipientEmail)
                .subject(subject)
                .templateName(templateName)
                .emailType(emailType)
                .sendStatus(SendStatus.SUCCESS)
                .messageId(messageId)
                .retryCount(0)
                .build();
    }

    public static EmailSendHistory createFailure(
            String recipientEmail,
            String subject,
            String templateName,
            EmailType emailType,
            String errorMessage) {
        return EmailSendHistory.builder()
                .recipientEmail(recipientEmail)
                .subject(subject)
                .templateName(templateName)
                .emailType(emailType)
                .sendStatus(SendStatus.FAILURE)
                .errorMessage(errorMessage)
                .retryCount(0)
                .build();
    }

    public void markRetryAttempt() {
        int currentRetryCount = this.retryCount == null ? 0 : this.retryCount;
        this.retryCount = currentRetryCount + 1;
        this.lastRetriedAt = LocalDateTime.now();
    }

    public void markSuccess(String messageId) {
        this.sendStatus = SendStatus.SUCCESS;
        this.messageId = messageId;
        this.errorMessage = null;
    }

    public void markFailure(String errorMessage) {
        this.sendStatus = SendStatus.FAILURE;
        this.errorMessage = errorMessage;
        this.messageId = null;
    }
}
