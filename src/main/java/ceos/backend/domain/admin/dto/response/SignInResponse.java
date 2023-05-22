package ceos.backend.domain.admin.dto.response;

import ceos.backend.domain.admin.dto.Token;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponse {

    private Token token;

    @Builder
    private SignInResponse(Token token){
        this.token = token;
    }

    public static SignInResponse from(Token token){
        return SignInResponse.builder()
                .token(token)
                .build();
    }
}
