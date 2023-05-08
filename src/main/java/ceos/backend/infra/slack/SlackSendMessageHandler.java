package ceos.backend.infra.slack;

import ceos.backend.global.common.dto.SlackErrorMessage;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;


@Component
@RequiredArgsConstructor
@Slf4j
public class SlackSendMessageHandler {
    private final SlackHelper slackHelper;

    @Async
    @EventListener(SlackErrorMessage.class)
    public void Handle(SlackErrorMessage slackErrorMessage) throws IOException {
        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        layoutBlocks.add(HeaderBlock.builder().text(plainText(slackErrorMessage.getContentCachingRequestWrapper().getRemoteAddr().toString())).build());
        layoutBlocks.add(HeaderBlock.builder().text(plainText(slackErrorMessage.getContentCachingRequestWrapper().getMethod().toString())).build());
        layoutBlocks.add(HeaderBlock.builder().text(plainText(slackErrorMessage.getContentCachingRequestWrapper().getRequestURL().toString())).build());
        final Payload payload =
                Payload.builder()
                        .text("에러 알림")
                        .username("유저")
                        .iconEmoji(":dog:")
                        .blocks(layoutBlocks)
                        .build();

        slackHelper.sendNotification(payload);
    }
}
