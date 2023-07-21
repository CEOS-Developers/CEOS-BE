package ceos.backend.domain.application.repository;


import ceos.backend.domain.application.domain.ApplicationQuestionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationQuestionDetailRepository
        extends JpaRepository<ApplicationQuestionDetail, Long> {}
