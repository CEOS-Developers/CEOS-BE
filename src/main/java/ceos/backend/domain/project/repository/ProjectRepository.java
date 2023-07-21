package ceos.backend.domain.project.repository;


import ceos.backend.domain.project.domain.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByNameAndGeneration(String name, int generation);

    List<Project> findByGeneration(int generation);

    @Query("SELECT MAX(p.generation) FROM Project p")
    int findMaxGeneration();
}
