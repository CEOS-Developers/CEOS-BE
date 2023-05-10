package ceos.backend.infra.ses;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Component
@RequiredArgsConstructor
public class AwsSESUtils {
    private final SesClient sesClient;
    private final SpringTemplateEngine templateEngine;

    public void singleEmailRequest(String to, String subject, String template, Context context) {
        final String html = templateEngine.process(template, context);

        final SendEmailRequest.Builder sendEmailRequestBuilder = SendEmailRequest.builder();
        sendEmailRequestBuilder
                .destination(
                        Destination.builder()
                                .toAddresses(to)
                                .build());
        sendEmailRequestBuilder
                .message(newMessage(subject, html))
                .source("ceos@ceos-sinchon.com")
                .build();

        sesClient.sendEmail(sendEmailRequestBuilder.build());
    }

    private Message newMessage(String subject, String html) {
        final Content content = Content.builder()
                .data(subject)
                .build();
        final Message message = Message.builder()
                .subject(content)
                .body(Body.builder()
                        .html(builder -> builder.data(html))
                        .build())
                .build();

        return message;
    }
}
