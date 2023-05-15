package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.ApplicationInterview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationInterviewRepository extends JpaRepository<ApplicationInterview, Long> {
}
