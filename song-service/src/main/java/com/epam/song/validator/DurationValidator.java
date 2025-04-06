package com.epam.song.validator;

import com.epam.song.annotation.DurationPattern;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DurationValidator implements ConstraintValidator<DurationPattern, String> {
  private static final Pattern TIME_PATTERN = Pattern.compile("^[0-5]\\d:[0-5]\\d$");

  @Override
  public void initialize(DurationPattern constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; // lets the @NotBlank annotation handle null values
    }
    return TIME_PATTERN.matcher(value).matches();
  }
}
