package ceos.backend.domain.application.repository;

import ceos.backend.domain.application.domain.ApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {
    @Query("select distinct aq from ApplicationQuestion aq" +
            " join fetch aq.applicationQuestionDetails")
    List<ApplicationQuestion> findAllWithQuestionDetail();
}
