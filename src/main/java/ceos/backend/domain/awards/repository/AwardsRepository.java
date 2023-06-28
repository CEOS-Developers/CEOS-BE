package ceos.backend.domain.awards.repository;

import ceos.backend.domain.awards.domain.Awards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AwardsRepository extends JpaRepository<Awards, Long> {
    List<Awards> findByGeneration(int generation);

}
