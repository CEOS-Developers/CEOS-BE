package ceos.backend.domain.retrospect.repository;


import ceos.backend.domain.retrospect.domain.Retrospect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetrospectRepository extends JpaRepository<Retrospect, Long> {
    Page<Retrospect> findAll(Pageable pageable);
}
