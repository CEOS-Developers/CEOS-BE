package ceos.backend.global.common.validator;

import ceos.backend.global.common.annotation.ValidDuration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DurationValidator implements ConstraintValidator<ValidDuration, String> {
    private final String pattern = "^\\d{4}([.]{1})(0[1-9]|1[012])([.]{1})(0[1-9]|[12][0-9]|3[01])([ ]{1})(0[0-9]|1[012])([:]{1})([0-5][0-9])([:]{1})([0-5][0-9])([ ]{1})([-]{1})([ ]{1})\\d{4}([.]{1})(0[1-9]|1[012])([.]{1})(0[1-9]|[12][0-9]|3[01])([ ]{1})(0[0-9]|1[012])([:]{1})([0-5][0-9])([:]{1})([0-5][0-9])$";
//private final String pattern = "^\\d{4}([.]{1})(0[1-9]|1[012])([.]{1})(0[1-9]|[12][0-9]|3[01])([ ]{1})(0[0-9]|1[012])([:]{1})([0-5][0-9])([:]{1})([0-5][0-9])$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)
            return false;
        return Pattern.matches(pattern, value);
    }
}
