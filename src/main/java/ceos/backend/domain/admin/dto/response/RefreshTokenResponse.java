package ceos.backend.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshTokenResponse {

    private String accessToken;

    @Builder
    private RefreshTokenResponse(String accessToken){
        this.accessToken = accessToken;
    }

    public static RefreshTokenResponse from(String accessToken){
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
