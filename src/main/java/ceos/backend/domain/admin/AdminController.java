package ceos.backend.domain.admin;

import ceos.backend.domain.admin.dto.request.*;
import ceos.backend.domain.admin.dto.response.CheckUsernameResponse;
import ceos.backend.domain.admin.dto.response.FindIdResponse;
import ceos.backend.domain.admin.dto.response.SignInResponse;
import ceos.backend.domain.admin.service.AdminService;
import ceos.backend.global.config.user.AdminDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Tag(name = "Admin")
public class AdminController {

    private final AdminService adminService;

    @Operation
    @PostMapping("/username")
    public CheckUsernameResponse checkUsername(@RequestBody @Valid CheckUsernameRequest checkUsernameRequest) {
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
    public SignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        log.info("로그인");
        return adminService.signIn(signInRequest);
    }

    @Operation(summary = "아이디 찾기")
    @PostMapping("/findid")
    public FindIdResponse findId(@RequestBody @Valid FindIdRequest findIdRequest) {
        log.info("아이디 찾기");
        return adminService.findId(findIdRequest);
    }

    @Operation(summary = "비밀번호 찾기")
    @PostMapping("/findpwd")
    public void findPwd(@RequestBody @Valid SendRandomPwdRequest sendRandomPwdRequest) {
        log.info("임시 비밀번호 메일 전송");
        adminService.findPwd(sendRandomPwdRequest);
    }

    @Operation(summary = "비밀번호 재설정")
    @PostMapping("/resetpwd")
    public void resetPwd(
            @RequestBody @Valid ResetPwdRequest resetPwdRequest,
            @AuthenticationPrincipal AdminDetails adminUser
    ) {
        log.info("비밀번호 재설정");
        adminService.resetPwd(resetPwdRequest, adminUser);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal AdminDetails adminUser) {
        log.info("로그아웃");
        adminService.logout(adminUser);
    }

//    @Operation(summary = "토큰 재발급")
//    @PostMapping("/refresh")
//    public RefreshTokenResponse refreshToken(
//            @RequestBody @Valid String refreshToken,
//            @AuthenticationPrincipal AdminDetails adminUser) {
//        log.info("토큰 재발급");
//        return adminService.refreshToken(refreshToken, adminUser);
//    }
}
