package ceos.backend.infra.ses;

import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

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
        final String SUBJECT = awsSESMailGenerator
                .generateApplicationMailSubject(request.getApplicationDetailVo()
                                                        .getGeneration());
        final Context CONTEXT = awsSESMailGenerator.generateApplicationMailContext(awsSESMail);
        awsSesUtils.singleEmailRequest(TO, SUBJECT, "sendApplicationMail", CONTEXT);
    }
}
