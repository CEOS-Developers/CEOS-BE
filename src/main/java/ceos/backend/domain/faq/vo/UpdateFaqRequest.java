package ceos.backend.domain.faq.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateFaqRequest {

    @Schema(description = "수정할 질문 내용")
    private String question;

    @Schema(description = "수정할 답변 내용")
    private String answer;
}
