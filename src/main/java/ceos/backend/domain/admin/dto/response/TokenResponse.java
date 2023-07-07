package ceos.backend.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    private TokenResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken =refreshToken;
    }

    public static TokenResponse of(String accessToken, String refreshToken){
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
