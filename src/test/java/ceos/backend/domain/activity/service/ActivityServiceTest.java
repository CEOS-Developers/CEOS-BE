package ceos.backend.domain.activity.service;

import ceos.backend.domain.activity.dto.ActivityRequest;
import ceos.backend.domain.activity.dto.ActivityResponse;
import ceos.backend.domain.activity.exception.ActivityNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    @DisplayName("활동 추가")
    void createActivity() {
        //given
        ActivityRequest activityRequest = ActivityRequest.builder()
                .name("해커톤")
                .content("해커톤은 좋아요")
                .imageUrl("hackathon.jpg")
                .build();

        //when
        Long id = activityService.createActivity(activityRequest).getId();
        ActivityResponse activityResponse = activityService.getActivity(id);

        //then
        assertThat(activityResponse.getName()).isEqualTo("해커톤");
    }

    @Test
    @DisplayName("활동 수정")
    void updateActivity() {
        //given
        ActivityRequest activityRequest = ActivityRequest.builder()
                .name("데모데이")
                .content("데모데이는 좋아요")
                .imageUrl("demoday.jpg")
                .build();

        Long id = activityService.createActivity(activityRequest).getId();

        //when
        ActivityRequest activityRequest2 = ActivityRequest.builder()
                .name("데모데이")
                .content("데모데이는 정말 좋아요")
                .imageUrl("demoday.jpg")
                .build();

        ActivityResponse activityResponse = activityService.updateActivity(id, activityRequest2);

        //then
        assertThat(activityResponse.getContent()).isEqualTo("데모데이는 정말 좋아요");
    }

    @Test
    @DisplayName("활동 삭제")
    void deleteActivity() {
        //given
        ActivityRequest activityRequest = ActivityRequest.builder()
                .name("데모데이")
                .content("데모데이는 좋아요")
                .imageUrl("demoday.jpg")
                .build();

        Long id = activityService.createActivity(activityRequest).getId();

        //when
        activityService.deleteActivity(id);

        //then
        assertThrows(ActivityNotFound.class, () -> {
            activityService.getActivity(id);
            throw new ActivityNotFound();
        });
    }
}