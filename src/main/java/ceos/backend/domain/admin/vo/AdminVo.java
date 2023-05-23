package ceos.backend.domain.admin.vo;

import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.annotation.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AdminVo {
    @Schema()
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String username;

    @Schema()
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @Schema()
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Schema()
    @ValidEnum(target = Part.class)
    @NotEmpty(message = "파트를 입력해주세요.")
    private Part part;

    @Schema(defaultValue = "ceos@ceos-sinchon.com", description = "운영진 이메일")
    @ValidEmail
    private String email;
}
