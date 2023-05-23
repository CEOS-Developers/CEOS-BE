package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import ceos.backend.global.common.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByNameAndEmail(String name, String email);
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByNameAndPartAndEmail(String name, Part part, String email);
    Optional<Admin> findByUsernameAndNameAndPartAndEmail(String username, String name, Part part, String email);
}
