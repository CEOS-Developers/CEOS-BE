package ceos.backend.domain.project.repository;

import ceos.backend.domain.project.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
