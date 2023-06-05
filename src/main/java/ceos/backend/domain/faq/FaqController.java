package ceos.backend.domain.faq;

import ceos.backend.domain.faq.dto.FaqDto;
import ceos.backend.domain.faq.dto.response.GetCategoryFaqResponse;
import ceos.backend.domain.faq.service.FaqService;
import ceos.backend.domain.faq.vo.FaqVo;
import ceos.backend.domain.faq.vo.UpdateFaqRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "카테고리별 질문, 답변 불러오기")
    @GetMapping
    public GetCategoryFaqResponse getCategoryFaq(@RequestParam(value = "category", defaultValue = "RECRUIT") String faqCategory) {
        log.info("카테고리별 질문, 답변 불러오기");
        return faqService.getCategoryFaq(faqCategory);
    }

    @Operation(summary = "FAQ 수정하기")
    @PatchMapping("/{faqId}")
    public FaqDto updateFaq(
            @PathVariable("faqId") Long faqId,
            @RequestBody UpdateFaqRequest updateFaqRequest
    ) {
        log.info("FAQ 수정하기");
        return faqService.updateFaq(faqId, updateFaqRequest);
    }

    @Operation(summary = "FAQ 삭제하기")
    @DeleteMapping("/{faqId}")
    public void deleteFaq(@PathVariable("faqId") Long faqId) {
        log.info("FAQ 삭제하기");
        faqService.deleteFaq(faqId);
    }
}
