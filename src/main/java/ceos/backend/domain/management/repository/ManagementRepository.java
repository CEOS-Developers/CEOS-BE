package ceos.backend.domain.management.repository;


import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.domain.ManagementRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagementRepository extends JpaRepository<Management, Long> {
    @Query("SELECT m FROM Management m WHERE m.role = :role ORDER BY m.name ASC")
    List<Management> findManagementAllByRoleOrderByNameAsc(@Param("role") ManagementRole role);
}
