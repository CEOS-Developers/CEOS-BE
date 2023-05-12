package ceos.backend.domain.awards.repository;

import ceos.backend.domain.awards.domain.Awards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardsRepository extends JpaRepository<Awards, Long> {
}
