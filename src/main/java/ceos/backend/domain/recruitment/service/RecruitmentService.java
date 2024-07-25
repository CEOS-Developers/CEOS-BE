package ceos.backend.domain.recruitment.service;


import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.dto.RecruitmentDTO;
import ceos.backend.domain.recruitment.dto.UserRecruitmentDTO;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentHelper recruitmentHelper;

    @Transactional(readOnly = true)
    public RecruitmentDTO getRecruitment() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        return RecruitmentDTO.from(recruitment);
    }

    public UserRecruitmentDTO getUserRecruitment() {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        return UserRecruitmentDTO.from(recruitment);
    }

    @Transactional
    public void updateRecruitment(RecruitmentDTO recruitmentDTO) {
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        recruitment.validAmenablePeriod(LocalDateTime.now());

        // 객체 업데이트
        recruitment.updateRecruitment(recruitmentDTO);
        recruitmentRepository.save(recruitment);
    }
}
