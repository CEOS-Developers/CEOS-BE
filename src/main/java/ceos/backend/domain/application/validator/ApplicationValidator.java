package ceos.backend.domain.application.validator;


import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.ApplicationQuestion;
import ceos.backend.domain.application.domain.Interview;
import ceos.backend.domain.application.domain.QuestionCategory;
import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.domain.application.exception.*;
import ceos.backend.domain.application.repository.ApplicationAnswerRepository;
import ceos.backend.domain.application.repository.ApplicationInterviewRepository;
import ceos.backend.domain.application.repository.ApplicationQuestionRepository;
import ceos.backend.domain.application.repository.ApplicationRepository;
import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.util.InterviewConvertor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {
    private final ApplicationRepository applicationRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final ApplicationInterviewRepository applicationInterviewRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;

    public void validateFirstApplication(ApplicantInfoVo applicantInfoVo) {
        if (applicationRepository.existsByEmail(applicantInfoVo.getEmail())) {
            throw DuplicateApplicant.EXCEPTION;
        }
    }

    public void validateApplicantAccessible(String uuid, String email) {
        applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public void validateApplicantDocumentPass(Application application) {
        application.validateDocumentPass();
    }

    public void validateApplicantInterviewCheckStatus(Application application) {
        application.validateDocumentPass();
        application.validateNotInterviewCheck();
    }

    public void validateApplicantActivityCheckStatus(Application application) {
        application.validateDocumentPass();
        application.validateFinalPass();
        application.validateNotFinalCheck();
    }

    public void validateExistingApplicant(Long applicationId) {
        applicationRepository
                .findById(applicationId)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
    }

    public void validateDocumentPassStatus(Application application) {
        application.validateDocumentPass();
    }

    public void validateInterviewTime(List<Interview> interviews, String interviewTime) {
        if (interviews.stream()
                .noneMatch(
                        interview ->
                                interviewTime.equals(
                                        InterviewConvertor.interviewDateFormatter(interview)))) {
            throw InterviewNotFound.EXCEPTION;
        }
    }

    public void validateRemainApplications() {
        if (applicationAnswerRepository.count() != 0) {
            throw AnswerStillExist.EXCEPTION;
        }
        if (applicationInterviewRepository.count() != 0) {
            throw ApplicationInterviewStillExist.EXCEPTION;
        }
    }

    public void validateQAMatching(CreateApplicationRequest createApplicationRequest) {
        final List<ApplicationQuestion> applicationQuestions =
                applicationQuestionRepository.findAll();
        if (applicationQuestions.stream()
                        .filter(question -> question.getCategory() == QuestionCategory.COMMON)
                        .count()
                != createApplicationRequest.getCommonAnswers().size()) {
            throw NotMatchingQnA.EXCEPTION;
        }
        final Part part = createApplicationRequest.getPart();
        if (applicationQuestions.stream()
                        .filter(
                                question ->
                                        question.getCategory().toString().equals(part.toString()))
                        .count()
                != createApplicationRequest.getPartAnswers().size()) {
            throw NotMatchingQnA.EXCEPTION;
        }
    }

    public void validateInterviewTimeExist(String uuid, String email) {
        Application application = applicationRepository
                .findByUuidAndEmail(uuid, email)
                .orElseThrow(
                        () -> {
                            throw ApplicantNotFound.EXCEPTION;
                        });
        if (application.getInterviewDatetime() == null) {
            throw NotSetInterviewTime.EXCEPTION;
        }
    }
}
