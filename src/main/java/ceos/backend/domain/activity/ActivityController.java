package ceos.backend.domain.activity;

import ceos.backend.domain.activity.dto.ActivityRequest;
import ceos.backend.domain.activity.dto.ActivityResponse;
import ceos.backend.domain.activity.dto.GetAllActivitiesResponse;
import ceos.backend.domain.activity.service.ActivityService;
import ceos.backend.global.common.dto.AwsS3Url;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/activities")
@Tag(name = "Activity")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "활동 생성하기")
    @PostMapping
    public void createActivity(@RequestBody @Valid ActivityRequest activityRequest) {
        log.info("활동 생성하기");
        activityService.createActivity(activityRequest);
    }


    @Operation(summary = "활동 조회하기")
    @GetMapping("/{id}")
    public ActivityResponse getActivity(@PathVariable Long id) {
        log.info("활동 조회하기");
        return activityService.getActivity(id);
    }


    @Operation(summary = "활동 전체 조회하기")
    @GetMapping
    public GetAllActivitiesResponse getAllActivities(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("limit") int limit
    ) {
        log.info("활동 전체 조회하기");
        return activityService.getAllActivities(pageNum, limit);
    }

    @Operation(summary = "활동 수정하기")
    @PutMapping("/{id}")
    public ActivityResponse updateActivity(@PathVariable Long id, @RequestBody @Valid ActivityRequest activityRequest) {
        log.info("활동 수정하기");
        return activityService.updateActivity(id, activityRequest);
    }


    @Operation(summary = "활동 삭제하기")
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        log.info("활동 삭제하기");
        activityService.deleteActivity(id);
    }


    @Operation(summary = "활동 이미지 url 생성하기")
    @GetMapping("/image")
    public AwsS3Url getImageUrl() {
        log.info("활동 이미지 url 생성하기");
        return activityService.getImageUrl();
    }
}
