package ceos.backend.domain.recruitment.service;

import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import org.springframework.transaction.annotation.Transactional;

public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public void getSettings(){

    }
}
