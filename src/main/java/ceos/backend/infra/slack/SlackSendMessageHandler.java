package ceos.backend.infra.slack;

import ceos.backend.global.common.dto.SlackErrorMessage;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class SlackSendMessageHandler {
    private final SlackMessageGenerater slackMessageGenerater;
    private final SlackHelper slackHelper;

    @Async
    @EventListener(SlackErrorMessage.class)
    public void Handle(SlackErrorMessage slackErrorMessage) throws IOException {
        Payload payload = slackMessageGenerater.generate(slackErrorMessage);
        slackHelper.sendNotification(payload);
    }
}
