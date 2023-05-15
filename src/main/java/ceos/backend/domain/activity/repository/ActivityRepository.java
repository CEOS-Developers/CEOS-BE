package ceos.backend.domain.activity.repository;

import ceos.backend.domain.activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
