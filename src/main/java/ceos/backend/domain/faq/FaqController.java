package ceos.backend.domain.faq;

import ceos.backend.domain.faq.service.FaqService;
import ceos.backend.domain.faq.vo.FaqVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/faq")
@Tag(name = "Faq")
public class FaqController {

    private final FaqService faqService;

    @Operation(summary = "FAQ 추가하기")
    @PostMapping
    public void createFaq(@RequestBody @Valid FaqVo faqVo) {
        log.info("FAQ 추가하기");
        faqService.createFaq(faqVo);
    }
}
