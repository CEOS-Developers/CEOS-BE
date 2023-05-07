package ceos.backend.infra.slack;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SlackHelper {
    @Value("${slack.webhook.dev_url}")
    String devUrl;

    @Value("${slack.webhook.prod_url}")
    String prodUrl;

    public void sendNotification(Payload payload) {
        final Slack slack = Slack.getInstance();

        try {
            slack.send(devUrl, payload);
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
}
