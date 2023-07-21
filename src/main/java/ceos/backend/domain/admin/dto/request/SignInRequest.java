package ceos.backend.domain.admin.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInRequest {

    @Schema(defaultValue = "string")
    @NotNull(message = "아이디를 입력해주세요.")
    private String username;

    @Schema(defaultValue = "string")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
