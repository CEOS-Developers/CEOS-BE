package ceos.backend.domain.admin.helper;

import ceos.backend.domain.settings.helper.SettingsHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ceos.backend.domain.admin.repository.AdminRepository;
import ceos.backend.domain.admin.vo.AdminVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminHelper {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SettingsHelper settingsHelper;
    private final AdminRepository adminRepository;

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public int takeGeneration() {
        return settingsHelper.takeSetting().getGeneration();
    }

    public void validateDuplicateAdmin(AdminVo adminVo) {
        if (adminRepository
                .findByNameAndEmail(adminVo.getName(), adminVo.getEmail())
                .isPresent()) {
            //throw DuplicateAdmin.EXCEPTION;
        }
    }

    public void validateUsername(AdminVo adminVo) {
        if (adminRepository
                .findByUsername(adminVo.getUsername())
                .isPresent()) {
            //throw DuplicateUsername.EXCEPTION;
        }
    }
}
