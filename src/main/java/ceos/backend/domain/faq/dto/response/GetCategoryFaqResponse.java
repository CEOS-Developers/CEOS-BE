package ceos.backend.domain.faq.dto.response;


import ceos.backend.domain.faq.dto.FaqDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCategoryFaqResponse {

    private List<FaqDto> categoryFaqList;

    @Builder
    private GetCategoryFaqResponse(List<FaqDto> categoryFaqList) {
        this.categoryFaqList = categoryFaqList;
    }

    public static GetCategoryFaqResponse from(List<FaqDto> categoryFaqList) {
        return GetCategoryFaqResponse.builder().categoryFaqList(categoryFaqList).build();
    }
}
