package ceos.backend.domain.settings.repository;

import ceos.backend.domain.settings.domain.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
