package ceos.backend.domain.activity.service;

import ceos.backend.domain.activity.domain.Activity;
import ceos.backend.domain.activity.dto.ActivityRequest;
import ceos.backend.domain.activity.dto.ActivityResponse;
import ceos.backend.domain.activity.dto.GetAllActivitiesResponse;
import ceos.backend.domain.activity.exception.ActivityNotFound;
import ceos.backend.domain.activity.converter.ActivityConverter;
import ceos.backend.domain.activity.repository.ActivityRepository;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityConverter activityConverter;
    private final AwsS3UrlHandler awsS3UrlHandler;


    /**
     * 활동 추가
     */
    @Transactional
    public void createActivity(ActivityRequest activityRequest) {
        Activity activity = Activity.from(activityRequest);
        activityRepository.save(activity);
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
    public GetAllActivitiesResponse getAllActivities(int pageNum, int limit) {
        //페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").descending());

        Page<Activity> pageActivities = activityRepository.findAll(pageRequest);

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageActivities.getTotalPages(), pageActivities.getTotalElements());

       // dto
        GetAllActivitiesResponse response = activityConverter.toActivitiesPage(pageActivities.getContent(), pageInfo);

        return response;
    }

    /**
     * 활동 수정
     */
    @Transactional
    public ActivityResponse updateActivity(Long id, ActivityRequest activityRequest) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityNotFound());

        activity.updateActivity(activityRequest);
        activityRepository.save(activity);

        return activityConverter.toDTO(activity);
    }

    /**
     * 활동 삭제
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
