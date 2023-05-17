package ceos.backend.global.error;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.common.dto.SlackErrorMessage;
import ceos.backend.global.common.event.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Nullable
    @Override
    @SneakyThrows
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final HttpStatus httpStatus = (HttpStatus)status;
        final List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        final Map<String, Object> fieldAndErrorMessages =
                errors.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField, FieldError::getDefaultMessage));
        final String errorsToJsonString = fieldAndErrorMessages.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue())
                .collect(Collectors.joining("|"));
        final ErrorReason errorReason = ErrorReason.from(status.value(), httpStatus.name(), errorsToJsonString);
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMessageNotReadableException", ex);
        final GlobalErrorCode globalErrorCode = GlobalErrorCode.HTTP_MESSAGE_NOT_READABLE;
        final ErrorReason errorReason = globalErrorCode.getErrorReason();
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
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

        // 슬랙 에러 알림
        final ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        final SlackErrorMessage errorMessage = SlackErrorMessage.from(e, cachingRequest);
        Event.raise(errorMessage);

        final GlobalErrorCode globalErrorCode = GlobalErrorCode._INTERNAL_SERVER_ERROR;
        final ErrorReason errorReason = globalErrorCode.getErrorReason();
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
