package ceos.backend.infra.slack;

import ceos.backend.global.common.dto.SlackErrorMessage;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.webhook.Payload;
import jakarta.servlet.ServletInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackMessageGenerater {
    private final int MaxLen = 500;

    public Payload generate(SlackErrorMessage slackErrorMessage) throws IOException {
        final ContentCachingRequestWrapper cachedRequest = slackErrorMessage.getContentCachingRequestWrapper();
        final Exception e = slackErrorMessage.getException();

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        // 제목
        layoutBlocks.add(HeaderBlock.builder().text(plainText("에러 알림")).build());
        // 구분선
        layoutBlocks.add(new DividerBlock());
        // IP + Method, Addr
        layoutBlocks.add(makeSection(getIP(cachedRequest), getAddr(cachedRequest)));
        // RequestBody + RequestParam
        layoutBlocks.add(makeSection(getBody(cachedRequest), getParam(cachedRequest)));
        // 구분선
        layoutBlocks.add(new DividerBlock());
        // IP + Method, Addr
        layoutBlocks.add(makeSection(getErrMessage(e), getErrStack(e)));

        final Payload payload =
                Payload.builder()
                        .text("에러 알림")
                        .username("에러")
                        .iconEmoji(":dog:")
                        .blocks(layoutBlocks)
                        .build();
        return payload;
    }

    private LayoutBlock makeSection(TextObject first, TextObject second ) {
        return Blocks.section(section -> section.fields(List.of(first, second)));
    }

    private MarkdownTextObject getIP(ContentCachingRequestWrapper c) {
        final String errorIP = c.getRemoteAddr();
        return MarkdownTextObject.builder().text("* User IP :*\n" + errorIP).build();
    }

    private MarkdownTextObject getAddr(ContentCachingRequestWrapper c) {
        final String method = c.getMethod();
        final String url = c.getRequestURL().toString();
        return MarkdownTextObject.builder()
                .text("* Request Addr :*\n" + method + " : " + url)
                .build();
    }

    private MarkdownTextObject getBody(ContentCachingRequestWrapper c) throws IOException {
        ServletInputStream inputStream = c.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return MarkdownTextObject.builder().text("* Request Body :*\n" + messageBody).build();
    }

    private MarkdownTextObject getParam(ContentCachingRequestWrapper c) {
        final String param = c.getQueryString();
        return MarkdownTextObject.builder().text("* Request Param :*\n" + param).build();
    }

    private MarkdownTextObject getErrMessage(Exception e) {
        final String errorMessage = e.getMessage();
        return MarkdownTextObject.builder().text("* Message :*\n" + errorMessage).build();
    }

    private MarkdownTextObject getErrStack(Throwable throwable) {
        String exceptionAsString = Arrays.toString(throwable.getStackTrace());
        int cutLength = Math.min(exceptionAsString.length(), MaxLen);
        String errorStack = exceptionAsString.substring(0, cutLength);
        return MarkdownTextObject.builder().text("* Stack Trace :*\n" + errorStack).build();
    }
}
