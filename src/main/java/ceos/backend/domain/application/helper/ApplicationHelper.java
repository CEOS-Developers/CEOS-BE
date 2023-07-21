package ceos.backend.domain.application.helper;

import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.enums.SortPartType;
import ceos.backend.domain.application.enums.SortPassType;
import ceos.backend.domain.application.exception.*;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.domain.recruitment.domain.Recruitment;
import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.event.Event;
import ceos.backend.global.util.InterviewDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationHelper {
    private final ApplicationMapper applicationMapper;
    private final ApplicationRepository applicationRepository;


    public Page<Application> getApplications(SortPassType docPass, SortPassType finalPass,
                                             SortPartType sortType, PageRequest pageRequest) {
        Page<Application> pageManagements = null;
        Part part = applicationMapper.toPart(sortType);
        if (docPass == SortPassType.ALL && finalPass == SortPassType.ALL) {
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAll(pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPart(applicationMapper.toPart(sortType), pageRequest);
            }
        } else if (docPass != SortPassType.ALL && finalPass == SortPassType.ALL) {
            Pass pass = applicationMapper.toPass(docPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAllByDocumentPass(pass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndDocumentPass(applicationMapper.toPart(sortType), pass, pageRequest);
            }
        } else if (docPass == SortPassType.ALL && finalPass != SortPassType.ALL){
            Pass pass = applicationMapper.toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAllByFinalPass(pass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndFinalPass(applicationMapper.toPart(sortType), pass, pageRequest);
            }
        } else {
            Pass convertedDocPass = applicationMapper.toPass(docPass);
            Pass convertedFinalPass = applicationMapper.toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository
                        .findAllByDocumentPassAndFinalPass(convertedDocPass, convertedFinalPass, pageRequest);
                default -> pageManagements = applicationRepository
                        .findAllByPartAndDocumentPassAndFinalPass(applicationMapper.toPart(sortType),
                                convertedDocPass,
                                convertedFinalPass,
                                pageRequest);
            }
        }
        return pageManagements;
    }
}
