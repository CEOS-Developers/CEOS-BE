package ceos.backend.infra.slack;

import ceos.backend.global.common.dto.SlackErrorMessage;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class SlackSendMessageHandler {
    private final SlackMessageGenerater slackMessageGenerater;
    private final SlackHelper slackHelper;

    @Async
    @EventListener(SlackErrorMessage.class)
    public void HandleError(SlackErrorMessage slackErrorMessage) throws IOException {
        Payload payload = slackMessageGenerater.generateErrorMsg(slackErrorMessage);
        slackHelper.sendErrorNotification(payload);
    }

    @Async
    @TransactionalEventListener(value = SlackUnavailableReason.class, phase = TransactionPhase.AFTER_COMMIT)
    public void HandleUnavailableReason(SlackUnavailableReason slackUnavailableReason) throws IOException {
        Payload payload = slackMessageGenerater.generateUnavailableReason(slackUnavailableReason);
        slackHelper.sendUnavailableReason(payload);
    }

}
