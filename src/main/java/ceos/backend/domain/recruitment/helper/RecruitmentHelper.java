package ceos.backend.domain.recruitment.helper;

import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.exception.RecruitmentNotFound;
import ceos.backend.domain.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitmentHelper {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment takeSetting() {
        return recruitmentRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> RecruitmentNotFound.EXCEPTION);
    }
}
