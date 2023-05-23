package ceos.backend.global.common.validator;

import ceos.backend.domain.application.vo.QuestionVo;
import ceos.backend.global.common.annotation.ValidQuestionOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class QuestionOrderValidator implements ConstraintValidator<ValidQuestionOrder, List<QuestionVo>> {
    @Override
    public boolean isValid(List<QuestionVo> values, ConstraintValidatorContext context) {
        if(values == null)
            return false;
        for (int i = 1; i <= values.size(); i++) {
            if (values.get(i - 1).getQuestionIndex() != i) {
                return false;
            }
        }
        return true;
    }
}
