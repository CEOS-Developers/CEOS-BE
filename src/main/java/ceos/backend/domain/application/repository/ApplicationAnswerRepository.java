package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
    List<ApplicationAnswer> findAllByApplication(Application application);
}
