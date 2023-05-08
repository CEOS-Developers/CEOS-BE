package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Getter
public class SlackErrorMessage {
    private Exception e;
    private ContentCachingRequestWrapper contentCachingRequestWrapper;

    @Builder
    public SlackErrorMessage(Exception e, ContentCachingRequestWrapper contentCachingRequestWrapper) {
        this.e = e;
        this.contentCachingRequestWrapper = contentCachingRequestWrapper;
    }

    public static SlackErrorMessage from(Exception e, ContentCachingRequestWrapper requestWrapper) {
        return SlackErrorMessage.builder()
                .e(e)
                .contentCachingRequestWrapper(requestWrapper)
                .build();
    }
}
