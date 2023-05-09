package ceos.backend.domain.admin.repository;

import ceos.backend.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
