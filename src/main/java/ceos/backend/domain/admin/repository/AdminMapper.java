package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.dto.request.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public Admin toEntity(SignUpRequest signUpRequest, String hashedPassword, int generation) {
        return Admin.of(signUpRequest, hashedPassword, generation);
    }
}
