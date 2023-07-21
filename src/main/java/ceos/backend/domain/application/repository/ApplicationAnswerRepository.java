package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationAnswer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
    List<ApplicationAnswer> findAllByApplication(Application application);
}
