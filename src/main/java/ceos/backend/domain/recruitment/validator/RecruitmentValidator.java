package ceos.backend.domain.recruitment.validator;


import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitmentValidator {
    private final RecruitmentHelper recruitmentHelper;

    public void validateBetweenStartDateDocAndEndDateDoc() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenStartDateDocAndEndDateDoc(LocalDateTime.now());
    }

    public void validateBeforeStartDateDoc() {
        recruitmentHelper.takeRecruitment().validateBeforeStartDateDoc(LocalDateTime.now());
    }

    public void validateBetweenResultDateDocAndResultDateFinal() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenResultDateDocAndResultDateFinal(LocalDateTime.now());
    }

    public void validateFinalResultAbleDuration() {
        recruitmentHelper.takeRecruitment().validateFinalResultAbleDuration(LocalDateTime.now());
    }

    public void validateBetweenStartDateDocAndResultDateDoc() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenStartDateDocAndResultDateDoc(LocalDateTime.now());
    }
}
