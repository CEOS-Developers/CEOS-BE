package ceos.backend.domain.faq.mapper;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.dto.FaqDto;
import ceos.backend.domain.faq.dto.response.GetCategoryFaqResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FaqMapper {

    public GetCategoryFaqResponse toCategoryFaqResponse(List<Faq> categoryFaqList) {
        List<FaqDto> categoryFaqDtoList = new ArrayList<>();
        for (Faq faq : categoryFaqList) {
            FaqDto faqDto = FaqDto.entityToDto(faq);
            categoryFaqDtoList.add(faqDto);
        }
        return GetCategoryFaqResponse.from(categoryFaqDtoList);
    }
}
