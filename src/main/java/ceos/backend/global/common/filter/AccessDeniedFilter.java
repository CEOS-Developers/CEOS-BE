package ceos.backend.global.common.filter;

import ceos.backend.global.error.BaseErrorCode;
import ceos.backend.global.error.BaseErrorException;
import ceos.backend.global.error.ErrorResponse;
import ceos.backend.global.error.exception.ForbiddenAdmin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AccessDeniedFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final String[] SwaggerPatterns = {
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return PatternMatchUtils.simpleMatch(SwaggerPatterns, servletPath);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseErrorException e) {
            responseToClient(response, getErrorResponse(e.getErrorCode()));
        } catch (AccessDeniedException e) {
            responseToClient(response, getErrorResponse(ForbiddenAdmin.EXCEPTION.getErrorCode()));
        }
    }

    private ErrorResponse getErrorResponse(BaseErrorCode errorCode) {

        return ErrorResponse.from(errorCode.getErrorReason());
    }

    private void responseToClient(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(Integer.parseInt(errorResponse.getStatus()));
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
