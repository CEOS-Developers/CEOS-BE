package ceos.backend.infra.ses;

import ceos.backend.global.common.dto.AwsSESPasswordMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsSESSendPasswordMailHandler {
    private final AwsSESUtils awsSesUtils;
    private final AwsSESPasswordMailGenerator awsSESPasswordMailGenerator;

    @EventListener(AwsSESPasswordMail.class)
    public void handle(AwsSESPasswordMail awsSESPasswordMail) {
        final String TO = awsSESPasswordMail.getEmail();
        final String SUBJECT = awsSESPasswordMailGenerator.generatePasswordMailSubject();
        final Context CONTEXT = awsSESPasswordMailGenerator.generatePasswordMailContext(awsSESPasswordMail);
        awsSesUtils.singleEmailRequest(TO, SUBJECT, "sendPasswordMail", CONTEXT);
    }
}
