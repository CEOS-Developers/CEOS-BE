package ceos.backend.domain.project.repository;


import ceos.backend.domain.project.domain.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUrlRepository extends JpaRepository<ProjectUrl, Long> {
    Optional<ProjectUrl> findByProjectAndCategory(Project project, ProjectUrlCategory category);

    List<ProjectUrl> findByCategory(ProjectUrlCategory category);
}
