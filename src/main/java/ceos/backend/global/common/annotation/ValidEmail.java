package ceos.backend.global.common.annotation;

import ceos.backend.global.common.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface ValidEmail {
    String message() default "올바른 형식의 값을 입력해주세요";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
