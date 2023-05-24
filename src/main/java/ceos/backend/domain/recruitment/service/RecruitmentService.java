package ceos.backend.domain.recruitment.service;

import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import ceos.backend.domain.recruitment.dto.response.GetRecruitmentResponse;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentHelper recruitmentHelper;

    @Transactional(readOnly = true)
    public GetRecruitmentResponse getRecruitment(){
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        return GetRecruitmentResponse.from(recruitment);
    }

    @Transactional
    public void updateRecruitment(UpdateRecruitmentRequest updateRecruitmentRequest){
        Recruitment recruitment = recruitmentHelper.takeRecruitment();

        //지원 기간동안 수정할 수 없음
        recruitment.validAmenablePeriod(LocalDate.now());

        // 객체 업데이트
        recruitment.updateRecruitment(updateRecruitmentRequest);
        recruitmentRepository.save(recruitment);
    }
}
