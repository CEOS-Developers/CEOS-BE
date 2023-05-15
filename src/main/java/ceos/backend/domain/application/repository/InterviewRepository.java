package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
