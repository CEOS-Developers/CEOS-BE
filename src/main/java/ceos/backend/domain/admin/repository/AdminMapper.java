package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.dto.request.SignUpRequest;
import ceos.backend.domain.admin.dto.response.CheckUsernameResponse;
import ceos.backend.domain.admin.dto.response.FindIdResponse;
import ceos.backend.domain.admin.dto.response.RefreshTokenResponse;
import ceos.backend.domain.admin.dto.response.SignInResponse;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public Admin toEntity(SignUpRequest signUpRequest, String encodedPassword, int generation) {
        return Admin.of(signUpRequest, encodedPassword, generation);
    }

    public CheckUsernameResponse toCheckUsernameResponse(boolean isAvailable) {
        return CheckUsernameResponse.from(isAvailable);
    }

    public SignInResponse toSignInResponse(String accessToken, String refreshToken) {
        return SignInResponse.of(accessToken, refreshToken);
    }

    public FindIdResponse toFindIdResponse(String username) {
        return FindIdResponse.from(username);
    }

    public RefreshTokenResponse toRefreshTokenResponse(String accessToken) {
        return RefreshTokenResponse.from(accessToken);
    }
}
