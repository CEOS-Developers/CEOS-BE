package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
}
