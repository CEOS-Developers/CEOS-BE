package ceos.backend.domain.admin;


import ceos.backend.domain.admin.dto.request.*;
import ceos.backend.domain.admin.dto.response.*;
import ceos.backend.domain.admin.service.AdminService;
import ceos.backend.global.config.user.AdminDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Tag(name = "Admin")
public class AdminController {

    private final AdminService adminService;
    private static final String MOBILE = "mobile";
    private static final String WEB = "web";

    @Operation(summary = "닉네임 확인")
    @PostMapping("/username")
    public CheckUsernameResponse checkUsername(
            @RequestBody @Valid CheckUsernameRequest checkUsernameRequest) {
        return adminService.checkUsername(checkUsernameRequest);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("회원가입");
        adminService.signUp(signUpRequest);
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public TokenResponse signIn(
            HttpServletRequest request, @RequestBody @Valid SignInRequest signInRequest) {
        log.info("로그인");
        String device = request.getHeader("User-Agent").contains("mobile") ? MOBILE : WEB;
        return adminService.signIn(device, signInRequest);
    }

    @Operation(summary = "아이디 찾기")
    @PostMapping("/id")
    public FindIdResponse findId(@RequestBody @Valid FindIdRequest findIdRequest) {
        log.info("아이디 찾기");
        return adminService.findId(findIdRequest);
    }

    @Operation(summary = "비밀번호 찾기")
    @PostMapping("/password")
    public void findPwd(@RequestBody @Valid SendRandomPwdRequest sendRandomPwdRequest) {
        log.info("임시 비밀번호 메일 전송");
        adminService.findPwd(sendRandomPwdRequest);
    }

    @Operation(summary = "비밀번호 재설정")
    @PostMapping("/newpassword")
    public void resetPwd(
            @RequestBody @Valid ResetPwdRequest resetPwdRequest,
            @AuthenticationPrincipal AdminDetails adminUser) {
        log.info("비밀번호 재설정");
        adminService.resetPwd(resetPwdRequest, adminUser);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request, @AuthenticationPrincipal AdminDetails adminUser) {
        log.info("로그아웃");
        String device = request.getHeader("User-Agent").contains("mobile") ? MOBILE : WEB;
        adminService.logout(device, adminUser);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public TokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        log.info("토큰 재발급");
        return adminService.reissueToken(refreshTokenRequest);
    }

    @Operation(summary = "슈퍼유저 - 유저 목록 보기")
    @GetMapping("/super")
    public GetAdminsResponse getAdmins(
            @AuthenticationPrincipal AdminDetails adminUser,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("limit") int limit) {
        log.info("슈퍼유저 - 유저 목록 보기");
        return adminService.getAdmins(adminUser, pageNum, limit);
    }

    @Operation(summary = "슈퍼유저 - 유저 권한 변경")
    @PostMapping("/super")
    public void grantAuthority(
            @AuthenticationPrincipal AdminDetails adminUser,
            @RequestBody @Valid GrantAuthorityRequest grantAuthorityRequest) {
        log.info("슈퍼유저 - 유저 권한 변경");
        adminService.grantAuthority(adminUser, grantAuthorityRequest);
    }

    @Operation(summary = "슈퍼유저 - 유저 삭제")
    @DeleteMapping("/super")
    public void deleteAdmin(@AuthenticationPrincipal AdminDetails adminUser, Long adminId) {
        log.info("슈퍼유저 - 유저 삭제");
        adminService.deleteAdmin(adminUser, adminId);
    }
}
