package ceos.backend.domain.recruitment.validator;


import ceos.backend.domain.recruitment.helper.RecruitmentHelper;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitmentValidator {
    private final RecruitmentHelper recruitmentHelper;

    public void validateBetweenStartDateDocAndEndDateDoc() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenStartDateDocAndEndDateDoc(LocalDate.now());
    }

    public void validateBeforeStartDateDoc() {
        recruitmentHelper.takeRecruitment().validateBeforeStartDateDoc(LocalDate.now());
    }

    public void validateBetweenResultDateDocAndResultDateFinal() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenResultDateDocAndResultDateFinal(LocalDate.now());
    }

    public void validateFinalResultAbleDuration() {
        recruitmentHelper.takeRecruitment().validateFinalResultAbleDuration(LocalDate.now());
    }

    public void validateBetweenStartDateDocAndResultDateDoc() {
        recruitmentHelper
                .takeRecruitment()
                .validateBetweenStartDateDocAndResultDateDoc(LocalDate.now());
    }
}
