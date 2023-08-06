package ceos.backend.domain.awards.repository;

import ceos.backend.domain.awards.domain.StartDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartDateRepository extends JpaRepository<StartDate, Integer> {
}
