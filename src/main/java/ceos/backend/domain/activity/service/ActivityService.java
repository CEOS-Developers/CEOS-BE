package ceos.backend.domain.activity.service;

import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityDto;
import ceos.backend.domain.activity.exception.ActivityNotFound;
import ceos.backend.domain.activity.repository.ActivityRepository;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.event.Event;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final AwsS3UrlHandler awsS3UrlHandler;

    /**
     * 활동 추가
     *
     * TODO : 인증
     */
    @Transactional
    public Activity createActivity(ActivityDto activityDto) {

        Activity activity = Activity.from(activityDto);
        activityRepository.save(activity);

        return activity;
    }

    /**
     * 활동 조회
     */
    @Transactional
    public Activity getActivity(Long id) {
        return activityRepository.findById(id).orElseThrow(()-> new ActivityNotFound());
    }

    /**
     * 활동 전체 조회
     */
    @Transactional
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * 활동 수정
     *
     * TODO : 인증
     */
    @Transactional
    public Activity updateActivity(Long id, ActivityDto activityDto) {

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFound());

        activity.updateActivity(activityDto);
        activityRepository.save(activity);

        return activity;
    }

    /**
     * 활동 삭제
     *
     * TODO : 인증
     */
    @Transactional
    public void deleteActivity(Long id) {

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFound());

        activityRepository.delete(activity);
    }
    

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl(){
        return awsS3UrlHandler.handle("activities");
    }
}
