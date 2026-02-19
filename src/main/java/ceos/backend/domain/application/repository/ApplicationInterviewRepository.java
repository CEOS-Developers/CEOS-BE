package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationInterview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationInterviewRepository extends JpaRepository<ApplicationInterview, Long> {
    List<ApplicationInterview> findAllByApplication(Application application);

    @Query(
            "select ai from ApplicationInterview ai"
                    + " join fetch ai.interview"
                    + " where ai.application = :application")
    List<ApplicationInterview> findAllByApplicationFetchInterview(
            @Param("application") Application application);
}
