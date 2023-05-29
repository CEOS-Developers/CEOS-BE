package ceos.backend.domain.activity.converter;

import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityResponse;
import org.springframework.stereotype.Component;

@Component
public class ActivityConverter {
    public ActivityResponse toDTO(Activity activity) {
        return ActivityResponse.from(activity);
    }
}
