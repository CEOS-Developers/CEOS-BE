package ceos.backend.domain.project.repository;


import ceos.backend.domain.project.domain.Participant;
import ceos.backend.domain.project.domain.Project;
import ceos.backend.global.common.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    void deleteAllByProjectAndPart(Project project, Part part);
}
