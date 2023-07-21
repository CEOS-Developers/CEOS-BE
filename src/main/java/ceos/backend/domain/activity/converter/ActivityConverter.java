package ceos.backend.domain.activity.converter;


import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityResponse;
import ceos.backend.domain.activity.dto.GetAllActivitiesResponse;
import ceos.backend.global.common.dto.PageInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ActivityConverter {
    public ActivityResponse toDTO(Activity activity) {
        return ActivityResponse.from(activity);
    }

    public GetAllActivitiesResponse toActivitiesPage(List<Activity> activities, PageInfo pageInfo) {
        List<ActivityResponse> activityResponses = new ArrayList<>();

        for (Activity activity : activities) {
            ActivityResponse activityResponse = ActivityResponse.from(activity);
            activityResponses.add(activityResponse);
        }
        return GetAllActivitiesResponse.of(activityResponses, pageInfo);
    }
}
