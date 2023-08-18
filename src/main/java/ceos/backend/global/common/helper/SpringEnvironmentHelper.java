package ceos.backend.global.common.helper;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEnvironmentHelper {
    private final Environment environment;

    private final String PROD = "prod";
    private final String DEV = "dev";

    public Boolean isProdProfile() {
        List<String> currentProfile = getCurrentProfile();
        return currentProfile.contains(PROD);
    }

    public Boolean isDevProfile() {
        List<String> currentProfile = getCurrentProfile();
        return currentProfile.contains(DEV);
    }

    public Boolean isProdAndDevProfile() {
        List<String> currentProfile = getCurrentProfile();
        return currentProfile.contains(PROD) || currentProfile.contains(DEV);
    }

    private List<String> getCurrentProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles).toList();
    }
}
