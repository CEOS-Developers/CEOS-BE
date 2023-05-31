package ceos.backend.domain.faq.service;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.domain.FaqCategory;
import ceos.backend.domain.faq.dto.FaqDto;
import ceos.backend.domain.faq.dto.response.GetCategoryFaqResponse;
import ceos.backend.domain.faq.exception.FaqNotFound;
import ceos.backend.domain.faq.mapper.FaqMapper;
import ceos.backend.domain.faq.repository.FaqRepository;
import ceos.backend.domain.faq.vo.FaqVo;
import ceos.backend.domain.faq.vo.UpdateFaqRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;

    @Transactional
    public void createFaq(FaqVo faqVo) {
        Faq newFaq = Faq.from(faqVo);
        faqRepository.save(newFaq);
    }

    @Transactional(readOnly = true)
    public GetCategoryFaqResponse getCategoryFaq(String faqCategory) {
        List<Faq> findCategoryFaqList = faqRepository.findAllByCategory(FaqCategory.parsing(faqCategory));
        return faqMapper.toCategoryFaqResponse(findCategoryFaqList);
    }

    @Transactional
    public FaqDto updateFaq(Long id, UpdateFaqRequest updateFaqRequest) {
        Faq findFaq = faqRepository.findById(id).orElseThrow(() -> {throw FaqNotFound.EXCEPTION;});
        findFaq.update(updateFaqRequest);
        return FaqDto.entityToDto(findFaq);
    }
}
