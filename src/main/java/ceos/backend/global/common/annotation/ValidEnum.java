package ceos.backend.global.common.annotation;

import ceos.backend.global.common.validator.EnumValidator;
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
@Constraint(validatedBy = {EnumValidator.class})
public @interface ValidEnum {
    String message() default "올바른 값을 입력해주세요";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends java.lang.Enum<?>> target();
}
