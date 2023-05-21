package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationInterview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationInterviewRepository extends JpaRepository<ApplicationInterview, Long> {
    List<ApplicationInterview> findAllByApplication(Application application);
}
