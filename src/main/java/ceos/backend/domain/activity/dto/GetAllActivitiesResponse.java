package ceos.backend.domain.activity.dto;


import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllActivitiesResponse {
    List<ActivityResponse> content;
    PageInfo pageInfo;

    @Builder
    private GetAllActivitiesResponse(List<ActivityResponse> activities, PageInfo pageInfo) {
        this.content = activities;
        this.pageInfo = pageInfo;
    }

    public static GetAllActivitiesResponse of(
            List<ActivityResponse> activities, PageInfo pageInfo) {
        return GetAllActivitiesResponse.builder().activities(activities).pageInfo(pageInfo).build();
    }
}
