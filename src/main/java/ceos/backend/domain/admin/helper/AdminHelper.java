package ceos.backend.domain.admin.helper;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.domain.AdminRole;
import ceos.backend.domain.admin.dto.request.FindIdRequest;
import ceos.backend.domain.admin.dto.request.ResetPwdRequest;
import ceos.backend.domain.admin.dto.request.SendRandomPwdRequest;
import ceos.backend.domain.admin.dto.request.SignInRequest;
import ceos.backend.domain.admin.exception.*;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESPasswordMail;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.config.jwt.TokenProvider;
import ceos.backend.global.config.user.AdminDetailsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ceos.backend.domain.admin.repository.AdminRepository;
import ceos.backend.domain.admin.vo.AdminVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminHelper {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RecruitmentHelper recruitmentHelper;
    private final AdminRepository adminRepository;
    private final AdminDetailsService adminDetailsService;
    private final TokenProvider tokenProvider;

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public int takeGeneration() {
        return recruitmentHelper.takeRecruitment().getGeneration();
    }

    public Authentication adminAuthorizationInput(Admin admin) {

        UserDetails userDetails = adminDetailsService.loadAdminByUsername(admin.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public String getAccessToken(Admin admin, Authentication authentication) {
        return tokenProvider.createAccessToken(admin.getId(), authentication);
    }

    public String getRefreshToken(Admin admin, Authentication authentication) {
        final String refreshToken = tokenProvider.createRefreshToken(authentication);

        admin.setRefreshToken(refreshToken);
        adminRepository.save(admin);

        return refreshToken;
    }

    public void findDuplicateUsername(String username) {
        if (adminRepository
                .findByUsername(username)
                .isPresent()) {
            throw DuplicateData.EXCEPTION;
        }
    }

    public void findDuplicateAdmin(AdminVo adminVo) {
        if (adminRepository
                .findByNameAndEmail(adminVo.getName(), adminVo.getEmail())
                .isPresent()) {
            throw DuplicateAdmin.EXCEPTION;
        }
    }

    public Admin findForSignIn(SignInRequest signInRequest) {
        Admin findAdmin = adminRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> {
                    throw AdminNotFound.EXCEPTION;
                });

        if (!matchesPassword(
                signInRequest.getPassword(),
                findAdmin.getPassword())) {
            throw MismatchPassword.EXCEPTION;
        }

        return findAdmin;
    }

    public Admin findForFindId(FindIdRequest findIdRequest) {
        return adminRepository
                .findByNameAndPartAndEmail(
                        findIdRequest.getName(),
                        findIdRequest.getPart(),
                        findIdRequest.getEmail())
                .orElseThrow(() -> {
                    throw AdminNotFound.EXCEPTION;
                });
    }

    public Admin findForSendRandomPwd(SendRandomPwdRequest sendRandomPwdRequest) {
        return adminRepository
                .findByUsernameAndNameAndPartAndEmail(
                        sendRandomPwdRequest.getUsername(),
                        sendRandomPwdRequest.getName(),
                        sendRandomPwdRequest.getPart(),
                        sendRandomPwdRequest.getEmail())
                .orElseThrow(() -> {
                    throw AdminNotFound.EXCEPTION;
                });
    }

    public void validateForResetPwd(ResetPwdRequest resetPwdRequest, Admin admin) {
        final String password = resetPwdRequest.getPassword();
        final String newPassword1 = resetPwdRequest.getNewPassword1();
        final String newPassword2 = resetPwdRequest.getNewPassword2();

        if (!matchesPassword(password, admin.getPassword())) {
            throw MismatchPassword.EXCEPTION;
        }

        if (!newPassword1.equals(newPassword2)) {
            throw MismatchNewPassword.EXCEPTION;
        }
    }

    public String generateRandomPwd() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public void setRandomPwd(Admin admin, String randomPwd) {
        admin.updateRandomPwd(randomPwd);
        adminRepository.save(admin);
    }

    public void sendEmail(String email, String name, String randomPwd) {
        Event.raise(AwsSESPasswordMail.of(email, name, randomPwd));
    }

    public void resetPwd(ResetPwdRequest resetPwdRequest, Admin admin) {
        final String encodedPassword = encodePassword(resetPwdRequest.getNewPassword1());
        admin.updatePwd(encodedPassword);
        adminRepository.save(admin);
    }

    public void deleteRefreshToken(Admin admin) {
        admin.deleteRefreshToken();
        adminRepository.save(admin);
    }

//    public void matchesRefreshToken(String refreshToken, Admin admin) {
//        if(!admin.getRefreshToken().equals(refreshToken)) {
//            throw .Exception;
//        }
//    }


    public Admin findAdmin(Long adminId){
        return adminRepository
                .findById(adminId)
                .orElseThrow(() -> {
                    throw AdminNotFound.EXCEPTION;
                });
    }

    public List<Admin> findAdmins(Admin superAdmin){
        return adminRepository.findAllByIdNot(superAdmin.getId());
    }

    public void changeRole(Admin admin, AdminRole adminRole) {
        admin.updateRole(adminRole);
    }
}
