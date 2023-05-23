package ceos.backend.domain.activity.service;

import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityRequest;
import ceos.backend.domain.activity.dto.ActivityResponse;
import ceos.backend.domain.activity.exception.ActivityNotFound;
import ceos.backend.domain.activity.converter.ActivityConverter;
import ceos.backend.domain.activity.repository.ActivityRepository;
import ceos.backend.domain.settings.domain.Settings;
import ceos.backend.domain.settings.helper.SettingsHelper;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityConverter activityConverter;
    private final AwsS3UrlHandler awsS3UrlHandler;
    private final SettingsHelper settingsHelper;



    /**
     * 활동 추가
     *
     * TODO : 인증
     */
    @Transactional
    public ActivityResponse createActivity(ActivityRequest activityRequest) {

        // 지원 기간 동안 수정할 수 없음
        Settings settings = settingsHelper.takeSetting();
        settings.validAmenablePeriod(LocalDate.now());

        Activity activity = Activity.from(activityRequest);
        activityRepository.save(activity);

        return activityConverter.toDTO(activity);
    }

    /**
     * 활동 조회
     */
    @Transactional(readOnly = true)
    public ActivityResponse getActivity(Long id) {
        return activityConverter.toDTO(activityRepository.findById(id).orElseThrow(()-> new ActivityNotFound()));
    }

    /**
     * 활동 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ActivityResponse> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        List<ActivityResponse> activitiesAsDto = new ArrayList<>();

        for (Activity activity : activities) {
            activitiesAsDto.add(activityConverter.toDTO(activity));
        }
        return activitiesAsDto;
    }

    /**
     * 활동 수정
     *
     * TODO : 인증
     */
    @Transactional
    public ActivityResponse updateActivity(Long id, ActivityRequest activityRequest) {

        // 지원 기간 동안 수정할 수 없음
        Settings settings = settingsHelper.takeSetting();
        settings.validAmenablePeriod(LocalDate.now());

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFound());

        activity.updateActivity(activityRequest);
        activityRepository.save(activity);

        return activityConverter.toDTO(activity);
    }

    /**
     * 활동 삭제
     *
     * TODO : 인증
     */
    @Transactional
    public void deleteActivity(Long id) {

        // 지원 기간 동안 수정할 수 없음
        Settings settings = settingsHelper.takeSetting();
        settings.validAmenablePeriod(LocalDate.now());

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFound());

        activityRepository.delete(activity);
    }
    

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl(){
        return awsS3UrlHandler.handle("activities");
    }
}
