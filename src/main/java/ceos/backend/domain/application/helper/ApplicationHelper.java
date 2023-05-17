package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.exception.DuplicateApplicant;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApplicationHelper {
    private final ApplicationRepository applicationRepository;

    public void validateFirstApplication(ApplicantInfoVo applicantInfoVo) {
        if (applicationRepository
                .findByNameAndPhoneNumber(applicantInfoVo.getName(), applicantInfoVo.getPhoneNumber())
                .isPresent()) {
            throw DuplicateApplicant.EXCEPTION;
        }
    }

    public String generateUUID() {
        String newUUID;
        while (true) {
            newUUID = UUID.randomUUID().toString();
            if (applicationRepository.findByUuid(newUUID).isEmpty()) {
                break;
            }
        }
        return newUUID;
    }
}
