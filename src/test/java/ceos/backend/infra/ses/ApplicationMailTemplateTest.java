package ceos.backend.infra.ses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

class ApplicationMailTemplateTest {

    @Test
    void rendersFixedWidthApplicationMailForClientScaling() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context(Locale.KOREAN);
        context.setVariable("greetInfo", Map.of("name", "지원자", "generation", "24"));
        context.setVariable("uuidInfo", Map.of("name", "지원자", "uuid", "CEOS-TEST-UUID"));
        context.setVariable(
                "personalInfo",
                Map.of(
                        "name", "지원자",
                        "gender", "여성",
                        "birth", "2000-01-01",
                        "email", "applicant@example.com",
                        "phoneNumber", "010-0000-0000"));
        context.setVariable(
                "schoolInfo",
                Map.of("university", "세오스대학교", "major", "컴퓨터공학", "semestersLeftNumber", "2"));
        context.setVariable(
                "ceosQuestionInfo",
                Map.of(
                        "otDate", "2026-09-02",
                        "demodayDate", "2027-02-06",
                        "otherActivities", "학업"));
        context.setVariable(
                "commonQuestionInfo",
                Map.of("questions", List.of("1 : 공통 질문"), "answers", List.of("공통 답변")));
        context.setVariable(
                "partQuestionInfo",
                Map.of(
                        "part", "개발",
                        "questions", List.of("2 : 파트 질문"),
                        "answers", List.of("파트 답변")));
        context.setVariable(
                "interviewDateInfo",
                Map.of(
                        "date", List.of("8월 28일 (금)"),
                        "notAvailabletime", List.of(List.of("19:00", "19:30"))));

        String html = templateEngine.process("sendApplicationMail", context);

        assertThat(html)
                .contains("name=\"viewport\" content=\"width=680\"")
                .contains("class=\"email-container\"")
                .contains("width=\"680\"")
                .contains("style=\"width: 680px; margin: 0 auto;\"")
                .contains("CEOS-TEST-UUID", "공통 답변", "파트 답변", "19:30")
                .doesNotContain("width=device-width")
                .doesNotContain("display: flex")
                .doesNotContain("width: fit-content");
        assertThat(html.indexOf("<html", html.indexOf("<html") + 1)).isEqualTo(-1);
    }
}
