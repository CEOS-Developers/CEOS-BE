package ceos.backend.infra.ses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

class RecruitMailTemplateTest {

    @Test
    void rendersFixedWidthRecruitMailForClientScaling() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context(Locale.KOREAN);
        context.setVariable("generation", 24);
        context.setVariable("startDateDoc", "8월 13일 (목)");
        context.setVariable("endDateDoc", "8월 23일 (일) 22:15");
        context.setVariable("resultDateDoc", "8월 26일 (수)");
        context.setVariable("startDateInterview", "8월 28일 (금)");
        context.setVariable("endDateInterview", "8월 31일 (월)");
        context.setVariable("resultDateFinal", "8월 31일 (월)");

        String html = templateEngine.process("sendRecruitMail", context);

        assertThat(html)
                .contains("name=\"viewport\" content=\"width=680\"")
                .contains("class=\"email-container\"")
                .contains("width=\"680\"")
                .contains("style=\"width: 680px; margin: 0 auto;\"")
                .contains("CEOS ", ">24<", "기 리크루팅을 시작합니다!")
                .doesNotContain("width=device-width")
                .doesNotContain("@media only screen")
                .doesNotContain("max-width: 720px")
                .doesNotContain("display: flex")
                .doesNotContain("width: fit-content");
        assertThat(html)
                .contains("height=\"26\"")
                .doesNotContain("width=\"82\"");
        assertThat(html.indexOf("<html", html.indexOf("<html") + 1)).isEqualTo(-1);
    }
}
