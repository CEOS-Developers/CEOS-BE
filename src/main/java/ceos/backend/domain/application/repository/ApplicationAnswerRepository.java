package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.ApplicationAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
}
