package ceos.backend.domain.admin.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {

    @Schema(defaultValue = "id")
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long userId;

    @Schema(defaultValue = "string")
    @NotNull(message = "리프레시 토큰을 입력해주세요.")
    private String refreshToken;
}
