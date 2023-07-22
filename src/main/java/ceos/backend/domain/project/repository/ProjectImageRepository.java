package ceos.backend.domain.project.repository;


import ceos.backend.domain.project.domain.Project;
import ceos.backend.domain.project.domain.ProjectImage;
import ceos.backend.domain.project.domain.ProjectImageCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    Optional<ProjectImage> findByProjectAndCategory(Project project, ProjectImageCategory category);
}
