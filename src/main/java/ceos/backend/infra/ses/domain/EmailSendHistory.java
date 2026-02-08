package ceos.backend.infra.ses.domain;


import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "email_send_history")
public class EmailSendHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder(access = AccessLevel.PRIVATE)
    private EmailSendHistory(
            String recipientEmail,
            String subject,
            String templateName,
            EmailType emailType,
            SendStatus sendStatus,
            String messageId,
            String errorMessage) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.templateName = templateName;
        this.emailType = emailType;
        this.sendStatus = sendStatus;
        this.messageId = messageId;
        this.errorMessage = errorMessage;
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
                .build();
    }
}
