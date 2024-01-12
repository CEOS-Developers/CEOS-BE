package ceos.backend.domain.startups.repository;

import ceos.backend.domain.startups.domain.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupRepository extends JpaRepository<Startup, Long> {

}
