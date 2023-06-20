package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.domain.ProjectImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    Optional<ProjectImage> findByProjectAndCategory(Project project, ProjectImageCategory category);
    List<ProjectImage> findByCategory(ProjectImageCategory category);
}
