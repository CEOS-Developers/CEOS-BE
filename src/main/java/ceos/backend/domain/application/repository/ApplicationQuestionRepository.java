package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
}
