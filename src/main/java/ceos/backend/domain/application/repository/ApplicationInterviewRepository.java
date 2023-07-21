package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationInterview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationInterviewRepository extends JpaRepository<ApplicationInterview, Long> {
    List<ApplicationInterview> findAllByApplication(Application application);
}
