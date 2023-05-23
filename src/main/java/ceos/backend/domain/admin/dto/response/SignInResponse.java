package ceos.backend.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    private SignInResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken =refreshToken;
    }

    public static SignInResponse of(String accessToken, String refreshToken){
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
