package ceos.backend.global.common.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpringEnvironmentHelper {
    private final Environment environment;

    private final String PROD = "prod";
    private final String DEV = "dev";

    public Boolean isProdProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).collect(Collectors.toList());
        return currentProfile.contains(PROD);
    }

    public Boolean isDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).collect(Collectors.toList());
        return currentProfile.contains(DEV);
    }
}
