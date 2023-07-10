package ceos.backend.domain.management.repository;

import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.domain.FaqCategory;
import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.domain.ManagementRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagementRepository extends JpaRepository<Management, Long> {
    @Query("SELECT m FROM Management m WHERE m.role = MANAGEMENT AND m.part = :part ORDER BY m.name")
    List<Management> findManagementAllByPartOrderByNameAsc(@Param("part") String part);
}
