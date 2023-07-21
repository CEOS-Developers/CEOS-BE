package ceos.backend.domain.management.helper;


import ceos.backend.domain.management.repository.ManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagementHelper {
    private final ManagementRepository managementRepository;
}
