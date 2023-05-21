package ceos.backend.domain.admin;

import ceos.backend.domain.admin.dto.request.FindIdRequest;
import ceos.backend.domain.admin.dto.request.SignUpRequest;
import ceos.backend.domain.admin.dto.response.FindIdResponse;
import ceos.backend.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("회원가입");
        adminService.signUp(signUpRequest);
    }

    @Operation(summary = "아이디 찾기")
    @PostMapping("/findid")
    public FindIdResponse findId(@RequestBody @Valid FindIdRequest findIdRequest) {
        log.info("아이디 찾기");
        return adminService.findId(findIdRequest);
    }
}
