package ceos.backend.infra.slack;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import ceos.backend.domain.application.domain.ApplicantInfo;
import ceos.backend.global.common.dto.SlackErrorMessage;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.webhook.Payload;
import jakarta.servlet.ServletInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackMessageGenerater {
    private final int MaxLen = 500;

    public Payload generateErrorMsg(SlackErrorMessage slackErrorMessage) throws IOException {
        final ContentCachingRequestWrapper cachedRequest =
                slackErrorMessage.getContentCachingRequestWrapper();
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

        return Payload.builder().text("에러 알림").blocks(layoutBlocks).build();
    }

    private LayoutBlock makeSection(TextObject first, TextObject second) {
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

    public Payload generateUnavailableReason(SlackUnavailableReason slackUnavailableReason) {
        final ApplicantInfo info = slackUnavailableReason.getApplication().getApplicantInfo();
        final String reason = slackUnavailableReason.getReason();
        final boolean isFinal = slackUnavailableReason.isFinal();

        final String title = isFinal ? "활동 불가능" : "면접 불가능";

        List<LayoutBlock> layoutBlocks = new ArrayList<>();
        // 제목
        layoutBlocks.add(HeaderBlock.builder().text(plainText(title)).build());
        // 구분선
        layoutBlocks.add(new DividerBlock());
        // name + uuid
        layoutBlocks.add(makeSection(getName(info), getUuid(info)));
        // reason
        layoutBlocks.add(getReason(reason));

        return Payload.builder().text(title).blocks(layoutBlocks).build();
    }

    private MarkdownTextObject getName(ApplicantInfo applicantInfo) {
        final String name = applicantInfo.getName();
        return MarkdownTextObject.builder().text("* Name :*\n" + name).build();
    }

    private MarkdownTextObject getUuid(ApplicantInfo applicantInfo) {
        final String UUID = applicantInfo.getUuid();
        return MarkdownTextObject.builder().text("* UUID :*\n" + UUID).build();
    }

    private SectionBlock getReason(String reason) {
        TextObject reasonObj =
                (TextObject) MarkdownTextObject.builder().text("* Reason :*\n" + reason).build();
        return Blocks.section(section -> section.fields(List.of(reasonObj)));
    }
}
