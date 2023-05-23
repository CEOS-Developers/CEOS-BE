package ceos.backend.domain.recruitment.service;

import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.dto.request.UpdateRecruitmentRequest;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentHelper recruitmentHelper;

    @Transactional(readOnly = true)
    public void getRecruitment(){
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
    }

    @Transactional
    public void updateRecruitment(UpdateRecruitmentRequest updateRecruitmentRequest){
        Recruitment recruitment = recruitmentHelper.takeRecruitment();
        recruitmentRepository.delete(recruitment);
        Recruitment updatedRecruitment = Recruitment.of(updateRecruitmentRequest);
        recruitmentRepository.save(updatedRecruitment)
    }
}
