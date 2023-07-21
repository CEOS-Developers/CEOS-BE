package ceos.backend.domain.application.helper;


import ceos.backend.domain.application.domain.*;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.dto.request.UpdateAttendanceRequest;
import ceos.backend.domain.application.enums.SortPartType;
import ceos.backend.domain.application.enums.SortPassType;
import ceos.backend.domain.application.exception.ApplicantNotFound;
import ceos.backend.domain.application.mapper.ApplicationMapper;
import ceos.backend.domain.application.repository.*;
import ceos.backend.global.common.dto.AwsSESMail;
import ceos.backend.global.common.dto.SlackUnavailableReason;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.event.Event;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationHelper {
    private final ApplicationMapper applicationMapper;
    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;

    public Page<Application> getApplications(
            SortPassType docPass,
            SortPassType finalPass,
            SortPartType sortType,
            PageRequest pageRequest) {
        Page<Application> pageManagements = null;
        Part part = toPart(sortType);
        if (docPass == SortPassType.ALL && finalPass == SortPassType.ALL) {
            switch (sortType) {
                case ALL -> pageManagements = applicationRepository.findAll(pageRequest);
                default -> pageManagements =
                        applicationRepository.findAllByPart(toPart(sortType), pageRequest);
            }
        } else if (docPass != SortPassType.ALL && finalPass == SortPassType.ALL) {
            Pass pass = toPass(docPass);
            switch (sortType) {
                case ALL -> pageManagements =
                        applicationRepository.findAllByDocumentPass(pass, pageRequest);
                default -> pageManagements =
                        applicationRepository.findAllByPartAndDocumentPass(
                                toPart(sortType), pass, pageRequest);
            }
        } else if (docPass == SortPassType.ALL && finalPass != SortPassType.ALL) {
            Pass pass = toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements =
                        applicationRepository.findAllByFinalPass(pass, pageRequest);
                default -> pageManagements =
                        applicationRepository.findAllByPartAndFinalPass(
                                toPart(sortType), pass, pageRequest);
            }
        } else {
            Pass convertedDocPass = toPass(docPass);
            Pass convertedFinalPass = toPass(finalPass);
            switch (sortType) {
                case ALL -> pageManagements =
                        applicationRepository.findAllByDocumentPassAndFinalPass(
                                convertedDocPass, convertedFinalPass, pageRequest);
                default -> pageManagements =
                        applicationRepository.findAllByPartAndDocumentPassAndFinalPass(
                                toPart(sortType),
                                convertedDocPass,
                                convertedFinalPass,
                                pageRequest);
            }
        }
        return pageManagements;
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

    public void sendEmail(CreateApplicationRequest request, int generation, String UUID) {
        final List<ApplicationQuestion> applicationQuestions =
                applicationQuestionRepository.findAll();
        Event.raise(AwsSESMail.of(request, applicationQuestions, generation, UUID));
    }

    public void sendSlackUnableReasonMessage(
            Application application, UpdateAttendanceRequest request, boolean isfinal) {
        final SlackUnavailableReason reason =
                SlackUnavailableReason.of(application, request.getReason(), isfinal);
        Event.raise(reason);
    }

    public Application getApplicationById(Long id) {
        return applicationRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public Application getApplicationByUuidAndEmail(String uuid, String email) {
        return applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    private Pass toPass(SortPassType passType) {
        Pass pass = Pass.FAIL;
        if (passType == SortPassType.PASS) {
            pass = Pass.PASS;
        }
        return pass;
    }

    private Part toPart(SortPartType sortType) {
        switch (sortType) {
            case DESIGN -> {
                return Part.DESIGN;
            }
            case BACKEND -> {
                return Part.BACKEND;
            }
            case PRODUCT -> {
                return Part.PRODUCT;
            }
            case FRONTEND -> {
                return Part.FRONTEND;
            }
        }
        return null;
    }
}
