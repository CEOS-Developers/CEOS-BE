package ceos.backend.domain.activity.dto;


import ceos.backend.domain.activity.domain.Activity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ActivityResponse {

    private Long id;

    private String name;

    private String content;

    private String imageUrl;

    @Builder
    private ActivityResponse(Long id, String name, String content, String imageUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static ActivityResponse from(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .content(activity.getContent())
                .imageUrl(activity.getImageUrl())
                .build();
    }
}
