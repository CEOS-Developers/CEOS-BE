package ceos.backend.domain.faq.repository;


import ceos.backend.domain.faq.domain.Faq;
import ceos.backend.domain.faq.domain.FaqCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    @Query("SELECT f FROM Faq f WHERE f.category = :category")
    List<Faq> findAllByCategory(@Param("category") FaqCategory category);
}
