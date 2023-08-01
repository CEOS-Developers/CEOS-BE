package ceos.backend.global.config;


import ceos.backend.global.config.jwt.JwtAccessDeniedHandler;
import ceos.backend.global.config.jwt.JwtAuthenticationEntryPoint;
import ceos.backend.global.config.jwt.JwtAuthenticationFilter;
import ceos.backend.global.config.jwt.JwtExceptionHandlerFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${server.url}")
    private String SERVER_URL;

    @Value("${server.user_url}")
    private String USER_URL;

    @Value("${server.admin_url}")
    private String ADMIN_URL;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final String[] SwaggerPatterns = {
        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    private final String[] AdminPatterns = {
        "/admin/newpassword",
        "/admin/logout",
        "/applications/**",
        "recruitments/**",
        "projects/**",
        "activities/**",
        "awards/**"
    };

    private final String[] GetPermittedPatterns = {
        "/awards/**",
        "recruitments/**",
        "projects/**",
        "activities/**",
        "/applications/question",
        "/applications/document",
        "/applications/final",
        "admin/reissue"
    };

    private final String[] PostPermittedPatterns = {"/applications"};

    private final String[] PatchPermittedPatterns = {
        "/applications/interview", "/applications/pass"
    };

    private final String[] RootPatterns = {"/admin/super"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .requestMatchers(HttpMethod.GET, GetPermittedPatterns)
                .permitAll()
                .requestMatchers(HttpMethod.PATCH, PatchPermittedPatterns)
                .permitAll()
                .requestMatchers(HttpMethod.POST, PostPermittedPatterns)
                .permitAll()
                .requestMatchers(SwaggerPatterns)
                .permitAll()
                .requestMatchers(AdminPatterns)
                .hasAnyRole("ROOT", "ADMIN")
                .requestMatchers(RootPatterns)
                .hasRole("ROOT")
                .anyRequest()
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    // @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", getDefaultCorsConfiguration());

        return source;
    }

    private CorsConfiguration getDefaultCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:8080",
                        "http://localhost:3000",
                        "http://localhost:3001",
                        USER_URL,
                        ADMIN_URL,
                        SERVER_URL));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        return configuration;
    }
}
