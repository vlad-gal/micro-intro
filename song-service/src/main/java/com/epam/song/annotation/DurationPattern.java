package com.epam.song.annotation;

import com.epam.song.validator.DurationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationValidator.class)
public @interface DurationPattern {
  String message() default "Duration must be in mm:ss format with leading zeros";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
