package ceos.backend.domain.sponsor.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SponsorVo {

    @Schema(defaultValue = "세오스", description = "후원사 이름")
    @NotEmpty(message = "후원사 이름을 입력해주세요")
    private String name;

    @Schema(
            defaultValue =
                    "https://s3.ap-northeast-2.amazonaws.com/ceos-sinchon.com-image/image/2490u509u020f",
            description = "사진 url")
    private String imageUrl;
}
