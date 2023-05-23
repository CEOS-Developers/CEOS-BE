package ceos.backend.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CheckUsernameRequest {

    @Schema(defaultValue = "string")
    @NotNull(message = "아이디를 입력해주세요.")
    private String username;
}
