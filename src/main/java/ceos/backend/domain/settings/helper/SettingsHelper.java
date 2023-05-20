package ceos.backend.domain.settings.helper;

import ceos.backend.domain.settings.domain.Settings;
import ceos.backend.domain.settings.exception.SettingsNotFound;
import ceos.backend.domain.settings.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettingsHelper {
    private final SettingsRepository settingsRepository;

    public Settings takeSetting() {
        return settingsRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> SettingsNotFound.EXCEPTION);
    }
}
