package ceos.backend.domain.startup.repository;


import ceos.backend.domain.startup.domain.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupRepository extends JpaRepository<Startup, Long> {}
