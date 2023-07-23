package ceos.backend.domain.admin.service;


import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.domain.AdminRole;
import ceos.backend.domain.admin.dto.request.*;
import ceos.backend.domain.admin.dto.response.*;
import ceos.backend.domain.admin.helper.AdminHelper;
import ceos.backend.domain.admin.repository.AdminMapper;
import ceos.backend.domain.admin.repository.AdminRepository;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.config.jwt.TokenProvider;
import ceos.backend.global.config.user.AdminDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final TokenProvider tokenProvider;
    private final AdminHelper adminHelper;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    @Transactional
    public CheckUsernameResponse checkUsername(CheckUsernameRequest checkUsernameRequest) {
        // 중복 아이디 검사
        adminHelper.findDuplicateUsername(checkUsernameRequest.getUsername());

        return adminMapper.toCheckUsernameResponse(true);
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        // 중복 어드민 검사
        adminHelper.findDuplicateAdmin(signUpRequest.getAdminVo());

        // 어드민 생성 및 저장
        final String encodedPassword =
                adminHelper.encodePassword(signUpRequest.getAdminVo().getPassword());
        final int generation = adminHelper.takeGeneration();
        final Admin admin = adminMapper.toEntity(signUpRequest, encodedPassword, generation);
        adminRepository.save(admin);
    }

    @Transactional
    public TokenResponse signIn(SignInRequest signInRequest) {

        final Admin admin = adminHelper.findForSignIn(signInRequest);
        final Authentication authentication = adminHelper.adminAuthorizationInput(admin);

        // 토큰 발급
        final String accessToken = tokenProvider.createAccessToken(admin.getId(), authentication);
        final String refreshToken = tokenProvider.createRefreshToken(admin.getId(), authentication);

        return adminMapper.toTokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public FindIdResponse findId(FindIdRequest findIdRequest) {
        // 어드민 검증
        final Admin admin = adminHelper.findForFindId(findIdRequest);

        return adminMapper.toFindIdResponse(admin.getUsername());
    }

    @Transactional
    public void findPwd(SendRandomPwdRequest sendRandomPwdRequest) {
        final Admin admin = adminHelper.findForSendRandomPwd(sendRandomPwdRequest);
        final String randomPwd = adminHelper.generateRandomPwd();

        // 임시 비밀번호 DB 저장
        adminHelper.setRandomPwd(admin, randomPwd);

        // 메일 전송
        adminHelper.sendEmail(admin.getEmail(), admin.getName(), randomPwd);
    }

    @Transactional
    public void resetPwd(ResetPwdRequest resetPwdRequest, AdminDetails adminUser) {
        final Admin admin = adminUser.getAdmin();

        // 입력값 검증
        adminHelper.validateForResetPwd(resetPwdRequest, admin);

        // 비밀번호 재설정
        adminHelper.resetPwd(resetPwdRequest, admin);
    }

    @Transactional
    public void logout(AdminDetails adminUser) {
        final Admin admin = adminUser.getAdmin();

        // 레디스 삭제
        tokenProvider.deleteRefreshToken(admin.getId());
    }

    @Transactional
    public TokenResponse reissueToken(
            RefreshTokenRequest refreshTokenRequest, AdminDetails adminUser) {
        final Admin admin = adminUser.getAdmin();
        final Authentication authentication = adminHelper.adminAuthorizationInput(admin);
        final String refreshToken = refreshTokenRequest.getRefreshToken();
        // 리프레시 토큰 검증
        tokenProvider.validateRefreshToken(refreshToken);
        adminHelper.matchesRefreshToken(refreshToken, admin);

        // 토큰 재발급
        final String newAccessToken =
                tokenProvider.createAccessToken(admin.getId(), authentication);
        final String newRefreshToken =
                tokenProvider.createRefreshToken(admin.getId(), authentication);

        return adminMapper.toTokenResponse(newAccessToken, newRefreshToken);
    }

    @Transactional(readOnly = true)
    public GetAdminsResponse getAdmins(AdminDetails adminUser, int pageNum, int limit) {
        final Admin superAdmin = adminUser.getAdmin();
        PageRequest pageRequest = PageRequest.of(pageNum, limit);
        Page<Admin> adminList = adminRepository.findAllByIdNot(pageRequest, superAdmin.getId());
        PageInfo pageInfo =
                PageInfo.of(
                        pageNum, limit, adminList.getTotalPages(), adminList.getTotalElements());
        return adminMapper.toGetAdmins(adminList, pageInfo);
    }

    @Transactional
    public void grantAuthority(
            AdminDetails adminUser, GrantAuthorityRequest grantAuthorityRequest) {
        final Admin superAdmin = adminUser.getAdmin();
        final Admin admin = adminHelper.findAdmin(grantAuthorityRequest.getId());
        final AdminRole adminRole = grantAuthorityRequest.getAdminRole();

        // 어드민 작업 검증
        adminHelper.validateAdmin(superAdmin, admin);

        // 권한 변경
        adminHelper.changeRole(admin, adminRole);
    }

    @Transactional
    public void deleteAdmin(AdminDetails adminUser, Long adminId) {
        final Admin superAdmin = adminUser.getAdmin();
        final Admin admin = adminHelper.findAdmin(adminId);

        // 어드민 작업 검증
        adminHelper.validateAdmin(superAdmin, admin);

        // 어드민 삭제
        adminRepository.delete(admin);
    }
}
