package ceos.backend.global.common.validator;


import ceos.backend.global.common.annotation.ValidDateList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class DateListValidator implements ConstraintValidator<ValidDateList, List<String>> {
    private final String pattern =
            "^\\d{4}([.]{1})(0[1-9]|1[012])([.]{1})(0[1-9]|[12][0-9]|3[01])$";

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) return false;
        for (String value : values) {
            if (!Pattern.matches(pattern, value)) return false;
        }
        return values.stream().distinct().count() == values.size();
    }
}
