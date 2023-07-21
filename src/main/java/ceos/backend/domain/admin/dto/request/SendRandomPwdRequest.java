package ceos.backend.domain.admin.dto.request;


import ceos.backend.global.common.annotation.ValidEmail;
import ceos.backend.global.common.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendRandomPwdRequest {
    @Schema(defaultValue = "string")
    @NotNull(message = "아이디를 입력해주세요.")
    private String username;

    @Schema(defaultValue = "string")
    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    @Schema()
    @NotNull(message = "파트를 입력해주세요.")
    private Part part;

    @Schema(defaultValue = "ceos@ceos-sinchon.com", description = "운영진 이메일")
    @ValidEmail
    private String email;
}
