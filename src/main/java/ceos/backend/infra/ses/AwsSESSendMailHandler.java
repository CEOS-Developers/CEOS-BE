package ceos.backend.infra.ses;


import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.AwsSESPasswordMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsSESSendMailHandler {
    private final AwsSESUtils awsSesUtils;
    private final AwsSESMailGenerator awsSESMailGenerator;

    @EventListener(AwsSESMail.class)
    public void handle(AwsSESMail awsSESMail) {
        final CreateApplicationRequest request = awsSESMail.getCreateApplicationRequest();

        final String TO = request.getApplicantInfoVo().getEmail();
        final String SUBJECT =
                awsSESMailGenerator.generateApplicationMailSubject(awsSESMail.getGeneration());
        final Context CONTEXT = awsSESMailGenerator.generateApplicationMailContext(awsSESMail);
        awsSesUtils.singleEmailRequest(TO, SUBJECT, "sendApplicationMail", CONTEXT);
    }

    @EventListener(AwsSESPasswordMail.class)
    public void handle(AwsSESPasswordMail awsSESPasswordMail) {
        final String TO = awsSESPasswordMail.getEmail();
        final String SUBJECT = awsSESMailGenerator.generatePasswordMailSubject();
        final Context CONTEXT = awsSESMailGenerator.generatePasswordMailContext(awsSESPasswordMail);
        awsSesUtils.singleEmailRequest(TO, SUBJECT, "sendPasswordMail", CONTEXT);
    }
}
