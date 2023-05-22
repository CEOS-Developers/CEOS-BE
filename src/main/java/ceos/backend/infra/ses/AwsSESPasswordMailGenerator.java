package ceos.backend.infra.ses;

import ceos.backend.global.common.dto.AwsSESPasswordMail;
import ceos.backend.global.common.dto.mail.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class AwsSESPasswordMailGenerator {

    public Context generatePasswordMailContext(AwsSESPasswordMail awsSESPasswordMail) {
        final String email = awsSESPasswordMail.getEmail();
        final String name = awsSESPasswordMail.getName();
        final String UUID = awsSESPasswordMail.getRandomPwd();

        Context context = new Context();
        context.setVariable("passwordInfo", PasswordInfo.of(email, name, UUID));

        return context;
    }

    public String generatePasswordMailSubject() {
        return "세오스 관리자 페이지 임시 비밀번호 발급";
    }
}
