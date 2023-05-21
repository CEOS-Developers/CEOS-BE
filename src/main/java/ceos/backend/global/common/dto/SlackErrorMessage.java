package ceos.backend.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Getter
public class SlackErrorMessage {
    private Exception exception;
    private ContentCachingRequestWrapper contentCachingRequestWrapper;

    @Builder
    private SlackErrorMessage(Exception exception, ContentCachingRequestWrapper contentCachingRequestWrapper) {
        this.exception = exception;
        this.contentCachingRequestWrapper = contentCachingRequestWrapper;
    }

    public static SlackErrorMessage of(Exception e, ContentCachingRequestWrapper requestWrapper) {
        return SlackErrorMessage.builder()
                .exception(e)
                .contentCachingRequestWrapper(requestWrapper)
                .build();
    }
}
