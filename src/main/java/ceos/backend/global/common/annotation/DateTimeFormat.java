package ceos.backend.global.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE_USE, FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
@JacksonAnnotationsInside
@JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy.MM.dd HH:mm:ss",
        timezone = "Asia/Seoul")
public @interface DateTimeFormat {}
