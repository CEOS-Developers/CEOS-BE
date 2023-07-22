package ceos.backend.domain.sponsor.repository;


import ceos.backend.domain.sponsor.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {}
