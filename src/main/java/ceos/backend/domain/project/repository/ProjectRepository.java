package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByNameAndGeneration(String name, int generation);
}
