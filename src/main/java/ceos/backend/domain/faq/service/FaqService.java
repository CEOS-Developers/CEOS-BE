package ceos.backend.domain.faq.service;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.repository.FaqRepository;
import ceos.backend.domain.faq.vo.FaqVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    @Transactional
    public void createFaq(FaqVo faqVo) {
        Faq newFaq = Faq.from(faqVo);
        faqRepository.save(newFaq);
    }
}
