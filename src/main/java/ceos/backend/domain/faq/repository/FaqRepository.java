package ceos.backend.domain.faq.repository;

import ceos.backend.domain.faq.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
