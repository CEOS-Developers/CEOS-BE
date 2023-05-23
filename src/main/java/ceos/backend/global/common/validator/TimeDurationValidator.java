package ceos.backend.global.common.validator;

import ceos.backend.global.common.annotation.ValidTimeDuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TimeDurationValidator implements ConstraintValidator<ValidTimeDuration, String> {
    private final String pattern = "(0[0-9]|1[012])([:]{1})([0-5][0-9])([:]{1})([0-5][0-9])([ ]{1})([-]{1})([ ]{1})(0[0-9]|1[012])([:]{1})([0-5][0-9])([:]{1})([0-5][0-9])";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)
            return false;
        return Pattern.matches(pattern, value);
    }
}
