package ceos.backend.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {

    @Schema(defaultValue = "access")
    @NotNull()
    private String accessToken;

    @Schema(defaultValue = "refresh")
    @NotNull()
    private String refreshToken;

    @Builder
    private Token(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Token from(String accessToken, String refreshToken){
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}