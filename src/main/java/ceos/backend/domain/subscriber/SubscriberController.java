package ceos.backend.domain.subscriber;


import ceos.backend.domain.subscriber.dto.request.SubscribeRequest;
import ceos.backend.domain.subscriber.service.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subscribe")
@Tag(name = "Subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Operation(summary = "메일링 서비스 구독")
    @PostMapping
    public void subscribeMail(@RequestBody @Valid SubscribeRequest subscribeRequest) {
        log.info("메일링 서비스 구독");
        subscriberService.subscribeMail(subscribeRequest);
    }

    @Operation(summary = "슈퍼유저 - 메일링 서비스")
    @GetMapping("/mail")
    public void setRecruitingMail() {
        log.info("슈퍼유저 - 메일링 서비스");
        subscriberService.sendRecruitingMail();
    }
}
