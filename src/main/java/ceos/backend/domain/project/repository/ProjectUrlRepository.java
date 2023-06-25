package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectUrlRepository extends JpaRepository<ProjectUrl, Long> {
    Optional<ProjectUrl> findByProjectAndCategory(Project project, ProjectUrlCategory category);
    List<ProjectUrl> findByCategory(ProjectUrlCategory category);
}
