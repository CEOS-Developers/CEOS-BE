package ceos.backend.domain.management.vo;


import ceos.backend.domain.management.domain.ManagementPart;
import ceos.backend.domain.management.domain.ManagementRole;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.University;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ManagementVo {

    @Schema(defaultValue = "세오스", description = "임원진 이름")
    @NotEmpty(message = "임원진 이름을 입력해주세요")
    private String name;

    @Schema(defaultValue = "회장단", description = "임원진 역할(회장단/총무/파트장/운영진/멘토)")
    @ValidEnum(target = ManagementRole.class)
    private ManagementRole role;

    @Schema(defaultValue = "기획", description = "임원진 파트(회장/부회장/공동회장/기획/디자인/프론트엔드/백엔드)")
    @ValidEnum(target = ManagementPart.class)
    private ManagementPart part;

    @Schema(defaultValue = "16", description = "기수")
    @Positive
    private int generation;

    @Schema(defaultValue = "17", description = "운영진 기수")
    @Positive
    @NotNull(message = "임원진 운영진 기수를 입력해주세요")
    private int managementGeneration;

    @Schema(defaultValue = "홍익대학교", description = "임원진 대학교")
    @ValidEnum(target = University.class)
    private University university;

    @Schema(defaultValue = "컴퓨터공학과", description = "임원진 전공")
    @NotEmpty(message = "임원진 전공을 입력해주세요")
    private String major;

    @Schema(defaultValue = "네이버", description = "임원진 소속")
    @NotEmpty(message = "임원진 소속을 입력해주세요")
    private String company;

    @Schema(
            defaultValue =
                    "https://s3.ap-northeast-2.amazonaws.com/ceos-sinchon.com-image/image/2490u509u020f",
            description = "사진 url")
    private String imageUrl;
}
