package ceos.backend.domain.faq.vo;

import ceos.backend.domain.faq.domain.FaqCategory;
import ceos.backend.global.common.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class FaqVo {

    @Schema(defaultValue = "PART", description = "질문 카테고리")
    @ValidEnum(target = FaqCategory.class)
    private FaqCategory category;

    @Schema(defaultValue = "지원하려면 어느 정도의 개발 관련 지식이나 경험이 필요한가요?", description = "질문 내용")
    @NotEmpty(message = "질문을 입력해주세요")
    private String question;

    @Schema(defaultValue = "기초적인 프로그래밍 능력만 있다면 가능합니다. 웹/앱 개발 경험이 없어도 개발 스터디에 잘 참여해 주신다면 충분히 가능합니다.", description = "답변 내용")
    @NotEmpty(message = "답변을 입력해주세요")
    private String answer;
}
