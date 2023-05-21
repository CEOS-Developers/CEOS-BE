package ceos.backend.domain.admin.service;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.dto.request.FindIdRequest;
import ceos.backend.domain.admin.dto.request.SignUpRequest;
import ceos.backend.domain.admin.dto.response.FindIdResponse;
import ceos.backend.domain.admin.helper.AdminHelper;
import ceos.backend.domain.admin.repository.AdminMapper;
import ceos.backend.domain.admin.repository.AdminRepository;
import ceos.backend.global.common.entity.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminHelper adminHelper;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        //중복 아이디 검사를 어디서 할까요 API를 새로 만들까요 아님 여기서 exception 던져서 처리할까요

        //중복 가입 검사
        adminHelper.validateDuplicateAdmin(signUpRequest.getAdminVo());

        final String hashedPassword = adminHelper.encodePassword(signUpRequest.getAdminVo().getPassword());
        final int generation = adminHelper.takeGeneration();
        final Admin admin = adminMapper.toEntity(signUpRequest, hashedPassword, generation);
        adminRepository.save(admin);
    }

    @Transactional
    public FindIdResponse findId(FindIdRequest findIdRequest) {

        final String name = findIdRequest.getName();
        final String email = findIdRequest.getEmail();
        final Part part = findIdRequest.getPart();

        Optional<Admin> admin = adminRepository.findIdBy(name, email, part);
        if (admin.isPresent()) {
            final String username = admin.get().getUsername();
            return FindIdResponse.from(username);
        }
        //throw WrongInput.EXCEPTION;
        //throw new Exception()
        final String username = admin.get().getUsername();
        return FindIdResponse.from(username);
    }
}
