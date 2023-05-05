package ceos.backend.global.common.response;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * status code가 200번일 경우,
 * response 형식을 변환해줍니다.
 */

@Slf4j
@RestControllerAdvice
public class ResponseAdvicer implements ResponseBodyAdvice<Object> {
    // 스웨거 오류날 경우, 이곳에 스웨거 url 추가 바람
    private final String[] SwaggerPatterns = {
            "swagger",
            "/v2/api-docs"
    };

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Nullable
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        // httpservletRequest에서 값을 사용할 경우, 그 값이 변경되므로 wrapper로 감싸서 사용해야합니다.
        // 추후 재사용을 위해 사용했습니다.
        ContentCachingRequestWrapper servletRequest
                = new ContentCachingRequestWrapper(((ServletServerHttpRequest) request).getServletRequest());

        for (String swaggerPattern : SwaggerPatterns) {
            if (servletRequest.getRequestURL().toString().contains(swaggerPattern))
                return body;
        }

        HttpStatus resolve = HttpStatus.resolve(servletResponse.getStatus());

        if (resolve == null)
            return body;

        if (resolve.is2xxSuccessful())
            return SuccessResponse.onSuccess(statusProvider(servletRequest.getMethod()), body);

        return body;
    }

    private int statusProvider(String method) {
        if (method.equals("POST"))
            return 201;
        if (method.equals("DELETE"))
            return 204;
        return 200;
    }
}
