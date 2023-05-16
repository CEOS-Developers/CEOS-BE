package ceos.backend.global.error;

import ceos.backend.global.common.dto.ErrorReason;
import ceos.backend.global.common.dto.SlackErrorMessage;
import ceos.backend.global.common.event.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
        if(ex instanceof MethodArgumentNotValidException Mex){
            final ErrorResponse errorResponse = this.handleMethodArgumentNotValid(Mex, headers,(HttpStatus)statusCode, request);
            return super.handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
        }
        final HttpStatus status = (HttpStatus) statusCode;
        final ErrorReason errorReason = ErrorReason.from(status.value(), status.name(), ex.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @SneakyThrows
    private ErrorResponse handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, Object> fieldAndErrorMessages =
                errors.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField, FieldError::getDefaultMessage));
        final String errorsToJsonString = new ObjectMapper().writeValueAsString(fieldAndErrorMessages);
        final ErrorReason errorReason = ErrorReason.from(status.value(), status.name(), errorsToJsonString);
        final ErrorResponse errorResponse = ErrorResponse.of(errorReason);
        return errorResponse;
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
