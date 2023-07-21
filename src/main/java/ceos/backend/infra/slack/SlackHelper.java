package ceos.backend.infra.slack;


import ceos.backend.global.common.helper.SpringEnvironmentHelper;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackHelper {
    private final SpringEnvironmentHelper springEnvironmentHelper;

    @Value("${slack.webhook.dev_url}")
    String devUrl;

    @Value("${slack.webhook.prod_url}")
    String prodUrl;

    @Value("${slack.webhook.unavailable_url}")
    String unavailableUrl;

    public void sendErrorNotification(Payload payload) {
        final Slack slack = Slack.getInstance();

        try {
            if (springEnvironmentHelper.isDevProfile()) {
                slack.send(devUrl, payload);
            } else {
                slack.send(prodUrl, payload);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendUnavailableReason(Payload payload) {
        final Slack slack = Slack.getInstance();

        try {
            slack.send(unavailableUrl, payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
