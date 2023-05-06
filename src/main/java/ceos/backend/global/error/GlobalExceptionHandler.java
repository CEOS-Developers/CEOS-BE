package ceos.backend.global.error;

import ceos.backend.global.common.dto.ErrorReason;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        log.error("HandleInternalException", ex);
        final HttpStatus status = (HttpStatus) statusCode;
        final ErrorReason errorReason = ErrorReason.from(status.value(), status.name(), ex.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    // 비즈니스 로직 에러 처리
    @ExceptionHandler(BaseErrorException.class)
    public ResponseEntity<ErrorResponse> handleBaseErrorException(BaseErrorException e, HttpServletRequest request) {
        log.error("BaseErrorException", e);
        final ErrorReason errorReason = e.getErrorCode().getErrorReason();
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }

    // 위에서 따로 처리하지 않은 에러를 모두 처리해줍니다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("Exception", e);
        final GlobalErrorCode globalErrorCode = GlobalErrorCode._INTERNAL_SERVER_ERROR;
        final ErrorReason errorReason = globalErrorCode.getErrorReason();
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
