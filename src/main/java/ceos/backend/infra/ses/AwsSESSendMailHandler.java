package ceos.backend.infra.ses;

import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.SlackErrorMessage;
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

    @EventListener(AwsSESMail.class)
    public void handle() {
        final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

        Context context = new Context();
        context.setVariable("test", "test1111");
        awsSesUtils.singleEmailRequest("wjdtkdgns10266@gmail.com", SUBJECT, "test", context);
    }
}
