package ceos.backend.domain.admin.service;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.domain.AdminRole;
import ceos.backend.domain.admin.dto.request.*;
import ceos.backend.domain.admin.dto.response.CheckUsernameResponse;
import ceos.backend.domain.admin.dto.response.FindIdResponse;
import ceos.backend.domain.admin.dto.response.GetAdminsResponse;
import ceos.backend.domain.admin.dto.response.SignInResponse;
import ceos.backend.domain.admin.helper.AdminHelper;
import ceos.backend.domain.admin.repository.AdminMapper;
import ceos.backend.domain.admin.repository.AdminRepository;
import ceos.backend.global.config.user.AdminDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminHelper adminHelper;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    @Transactional
    public CheckUsernameResponse checkUsername(CheckUsernameRequest checkUsernameRequest) {
        //중복 아이디 검사
        adminHelper.findDuplicateUsername(checkUsernameRequest.getUsername());

        return adminMapper.toCheckUsernameResponse(true);
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        //중복 어드민 검사
        adminHelper.findDuplicateAdmin(signUpRequest.getAdminVo());

        //어드민 생성 및 저장
        final String encodedPassword = adminHelper.encodePassword(signUpRequest.getAdminVo().getPassword());
        final int generation = adminHelper.takeGeneration();
        final Admin admin = adminMapper.toEntity(signUpRequest, encodedPassword, generation);
        adminRepository.save(admin);
    }

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {

        final Admin admin = adminHelper.findForSignIn(signInRequest);
        final Authentication authentication = adminHelper.adminAuthorizationInput(admin);

        //토큰 발급
        final String accessToken = adminHelper.getAccessToken(admin, authentication);
        final String refreshToken = adminHelper.getRefreshToken(admin, authentication);

        return adminMapper.toSignInResponse(accessToken, refreshToken);
    }

    @Transactional
    public FindIdResponse findId(FindIdRequest findIdRequest) {
        //어드민 검증
        final Admin admin = adminHelper.findForFindId(findIdRequest);

        return adminMapper.toFindIdResponse(admin.getUsername());
    }

    @Transactional
    public void findPwd(SendRandomPwdRequest sendRandomPwdRequest) {
        final Admin admin = adminHelper.findForSendRandomPwd(sendRandomPwdRequest);
        final String randomPwd = adminHelper.generateRandomPwd();

        //임시 비밀번호 DB 저장
        adminHelper.setRandomPwd(admin, randomPwd);

        //메일 전송
        adminHelper.sendEmail(admin.getEmail(), admin.getName(), randomPwd);
    }

    @Transactional
    public void resetPwd(ResetPwdRequest resetPwdRequest, AdminDetails adminUser) {
        final Admin admin = adminUser.getAdmin();

        //입력값 검증
        adminHelper.validateForResetPwd(resetPwdRequest, admin);

        //비밀번호 재설정
        adminHelper.resetPwd(resetPwdRequest, admin);
    }

    @Transactional
    public void logout(AdminDetails adminUser) {
        final Admin admin = adminUser.getAdmin();

        adminHelper.deleteRefreshToken(admin);
    }

//    @Transactional
//    public RefreshTokenResponse refreshToken(String refreshToken, AdminDetails adminUser) {
//        final Admin admin = adminUser.getAdmin();
//        final Authentication authentication = adminHelper.adminAuthorizationInput(admin);
//        //리프레시 토큰 검증
//        tokenProvider.validateToken(refreshToken);
//        adminHelper.matchesRefreshToken()
//
//        //토큰 재발급
//        final String accessToken = adminHelper.getAccessToken(admin, authentication);
//
//        return adminMapper.toRefreshTokenResponse(accessToken);
//    }

    @Transactional
    public GetAdminsResponse getAdmins(AdminDetails adminUser) {
        final Admin superAdmin = adminUser.getAdmin();
        return adminMapper.toGetAdmins(adminHelper.findAdmins(superAdmin));
    }

    @Transactional
    public void grantAuthority(AdminDetails adminUser, GrantAuthorityRequest grantAuthorityRequest) {
        final Admin admin = adminHelper.findAdmin(grantAuthorityRequest.getId());
        final AdminRole adminRole = grantAuthorityRequest.getAdminRole();

        //권한 변경
        adminHelper.changeRole(admin, adminRole);
    }
}
