package ceos.backend.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ResetPwdRequest {

    @Schema(defaultValue = "string")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @Schema(defaultValue = "new_string")
    @NotNull(message = "새 비밀번호를 입력해주세요.")
    private String newPassword1;

    @Schema(defaultValue = "new_string")
    @NotNull(message = "새 비밀번호를 입력해주세요.")
    private String newPassword2;
}
