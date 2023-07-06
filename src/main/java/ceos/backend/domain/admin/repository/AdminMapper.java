package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.dto.request.SignUpRequest;
import ceos.backend.domain.admin.dto.response.*;
import ceos.backend.domain.admin.vo.AdminBriefInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMapper {
    public Admin toEntity(SignUpRequest signUpRequest, String encodedPassword, int generation) {
        return Admin.of(signUpRequest, encodedPassword, generation);
    }

    public CheckUsernameResponse toCheckUsernameResponse(boolean isAvailable) {
        return CheckUsernameResponse.from(isAvailable);
    }

    public TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.of(accessToken, refreshToken);
    }

    public FindIdResponse toFindIdResponse(String username) {
        return FindIdResponse.from(username);
    }

    public GetAdminsResponse toGetAdmins(List<Admin> adminList) {

        List<AdminBriefInfoVo> adminBriefInfoVos = adminList.stream()
                .map(AdminBriefInfoVo::from)
                .toList();
        return GetAdminsResponse.from(adminBriefInfoVos);
    }
}
