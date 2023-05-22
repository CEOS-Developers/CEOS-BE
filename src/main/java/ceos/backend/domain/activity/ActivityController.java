package ceos.backend.domain.activity;

import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityDto;
import ceos.backend.domain.activity.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/activities")
@Tag(name = "Activity")
public class ActivityController {

    private final ActivityService activityService;


    @Operation(summary = "활동 생성하기")
    @PostMapping
    public Activity createActivity(@RequestBody @Valid ActivityDto activityDto) {
        log.info("활동 생성하기");
        return activityService.createActivity(activityDto);
    }


    @Operation(summary = "활동 조회하기")
    @GetMapping("/{id}")
    public Activity getActivity(@PathVariable @Valid Long id) {
        log.info("활동 조회하기");
        return activityService.getActivity(id);
    }


    @Operation(summary = "활동 전체 조회하기")
    @GetMapping
    public List<Activity> getAllActivities() {
        log.info("활동 전체 조회하기");
        return activityService.getAllActivities();
    }


    @Operation(summary = "활동 수정하기")
    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable @Valid Long id, @RequestBody @Valid ActivityDto activityDto) {
        log.info("활동 수정하기");
        return activityService.updateActivity(id, activityDto);
    }


    @Operation(summary = "활동 삭제하기")
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable @Valid Long id) {
        log.info("활동 삭제하기");
        activityService.deleteActivity(id);
    }

}
