package ceos.backend.domain.management.repository;

import ceos.backend.domain.management.domain.Management;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementRepository extends JpaRepository<Management, Long> {
}
