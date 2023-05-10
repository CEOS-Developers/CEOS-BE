package ceos.backend.infra.ses;

import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsSESSendMailHandler {
    private final AwsSESUtils awsSesUtils;

    @EventListener(AwsSESMail.class)
    public void handle() {
        // TODO : 00에 기수 정보 채우기
        final String SUBJECT = "세오스 00기 지원 알림드립니다.";

        List<String> q = new ArrayList<>(), a = new ArrayList<>();
        q.add("1. 유망하다고 생각하는 시장과 그 이유에 대해 서술해주세요.");
        q.add("2. 공통 질문에 적어주신 ‘하고 싶은 창업 아이템'의 시장성을 평가해주세요.");
        q.add("3. 창업 및 스타트업에 대한 자신의 관심을 자유롭게 서술해주세요.");
        a.add("흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. 무성할 하나에 내 차 걱정도 말 봅니다. 둘 내린 노새, 하나에 내 있습니다. 써 위에 자랑처럼 하늘에는 소녀들의 멀듯이, 하나에 옥 시와 봅니다. 겨울이 덮어 가을 계십니다. 노새, 애기 사랑과 자랑처럼 그러나 겨울이 릴케 거외다. 이름과, 하나의 가난한 이름자 아직 까닭입니다. 청춘이 아름다운 경, 별이 나는 노루, 하나에 듯합니다. 별빛이 나의 나는 하나에 하나에 무성할 사랑과 계절이 흙으로 듯합니다. 너무나 봄이 마리아 추억과 봅니다. 흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. ");
        a.add("흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. 무성할 하나에 내 차 걱정도 말 봅니다. 둘 내린 노새, 하나에 내 있습니다. 써 위에 자랑처럼 하늘에는 소녀들의 멀듯이, 하나에 옥 시와 봅니다. 겨울이 덮어 가을 계십니다. 노새, 애기 사랑과 자랑처럼 그러나 겨울이 릴케 거외다. 이름과, 하나의 가난한 이름자 아직 까닭입니다. 청춘이 아름다운 경, 별이 나는 노루, 하나에 듯합니다. 별빛이 나의 나는 하나에 하나에 무성할 사랑과 계절이 흙으로 듯합니다. 너무나 봄이 마리아 추억과 봅니다. 흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. ");
        a.add("흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. 무성할 하나에 내 차 걱정도 말 봅니다. 둘 내린 노새, 하나에 내 있습니다. 써 위에 자랑처럼 하늘에는 소녀들의 멀듯이, 하나에 옥 시와 봅니다. 겨울이 덮어 가을 계십니다. 노새, 애기 사랑과 자랑처럼 그러나 겨울이 릴케 거외다. 이름과, 하나의 가난한 이름자 아직 까닭입니다. 청춘이 아름다운 경, 별이 나는 노루, 하나에 듯합니다. 별빛이 나의 나는 하나에 하나에 무성할 사랑과 계절이 흙으로 듯합니다. 너무나 봄이 마리아 추억과 봅니다. 흙으로 별 지나가는 나의 묻힌 까닭이요, 까닭입니다. ");

        List<String> date = new ArrayList<>();
        date.add("11/11");
        date.add("11/12");
        date.add("11/13");
        List<String> time = new ArrayList<>();
        time.add("11:00-11:30");
        time.add("12:00-12:30");
        time.add("13:00-13:30");
        List<List<String>> times = new ArrayList<>();
        times.add(time);
        times.add(time);
        times.add(time);


        Context context = new Context();
        context.setVariable("greetInfo", GreetInfo.of("1123","asdasd"));
        context.setVariable("uuidInfo", UuidInfo.of("1123","uuidasd"));
        context.setVariable("personalInfo", PersonalInfo.of("1122","gender","birth","email","phone"));
        context.setVariable("schoolInfo", SchoolInfo.of("학교 이름","메이져", "100"));
        context.setVariable("ceosQuestionInfo", CeosQuestionInfo.of("ㅇㅇ","ㅇㅇ","백수 예정"));
        context.setVariable("commonQuestionInfo",CommonQuestionInfo.of(q,a));
        context.setVariable("partQuestionInfo",PartQuestionInfo.of("파트이름",q,a));
        context.setVariable("interviewDateInfo", InterviewDateInfo.of(times, date));
        awsSesUtils.singleEmailRequest("wjdtkdgns10266@gmail.com", SUBJECT, "sendApplicationMail", context);
    }
}
