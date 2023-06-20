package ceos.backend.global.config.user;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("adminDetailsService")
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    public final AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public UserDetails loadAdminByUsername(Long id) throws UsernameNotFoundException {
        return adminRepository.findById(id)
                .map(admin -> createAdmin(id, admin))
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> DB에서 찾을 수 없음"));
    }

    private AdminDetails createAdmin(Long id, Admin admin) {
        return new AdminDetails(admin);
    }
}
