package ceos.backend.domain.activity.dto;

import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllActivitiesResponse {
    List<ActivityResponse> activities;
    PageInfo pageInfo;

    @Builder
    private GetAllActivitiesResponse(List<ActivityResponse> activities, PageInfo pageInfo) {
        this.activities = activities;
        this.pageInfo = pageInfo;
    }

    public static GetAllActivitiesResponse of(List<ActivityResponse> activities, PageInfo pageInfo) {
        return GetAllActivitiesResponse.builder()
                .activities(activities)
                .pageInfo(pageInfo)
                .build();
    }
}
