package ceos.backend.global.config;


import ceos.backend.global.common.filter.AccessDeniedFilter;
import ceos.backend.global.common.helper.SpringEnvironmentHelper;
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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration()
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

    @Value("${server.dev_url}")
    private String DEV_URL;

    @Value("${swagger.user}")
    private String swaggerUser;

    @Value("${swagger.pwd}")
    private String swaggerPassword;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final SpringEnvironmentHelper springEnvironmentHelper;
    private final AccessDeniedFilter accessDeniedFilter;

    private final String[] SwaggerPatterns = {
        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    private final String[] AdminPatterns = {
        "/admin/newpassword",
        "/admin/logout",
        "/applications/**",
        "/recruitments/**",
        "/projects/**",
        "/activities/**",
        "/awards/**",
        "/managements/**",
        "/faq/**",
        "/sponsors/**",
        "/start-ups/**"
    };

    private final String[] GetPermittedPatterns = {
        "/awards/**",
        "/recruitments",
        "/projects/**",
        "/activities/**",
        "/managements/**",
        "/faq/**",
        "/sponsors/**",
        "/applications/question",
        "/applications/document",
        "/applications/final",
        "/admin/reissue",
        "/start-ups/**"
    };

    private final String[] PostPermittedPatterns = {"/applications"};

    private final String[] PatchPermittedPatterns = {
        "/applications/interview", "/applications/pass"
    };

    private final String[] RootPatterns = {"/admin/super", "/subscribe/mail"};

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername(swaggerUser)
                        .password(passwordEncoder().encode(swaggerPassword))
                        .roles("SWAGGER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        if (springEnvironmentHelper.isProdAndDevProfile()) {
            http.authorizeHttpRequests()
                    .requestMatchers(SwaggerPatterns)
                    .authenticated()
                    .and()
                    .httpBasic(Customizer.withDefaults());
        }

        http.authorizeHttpRequests()
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

        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
        //                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
        http.addFilterBefore(accessDeniedFilter, AuthorizationFilter.class);

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
                        SERVER_URL,
                        DEV_URL));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        return configuration;
    }
}
