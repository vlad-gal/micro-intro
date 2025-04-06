package com.epam.song.validator;

import com.epam.song.annotation.YearRange;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<YearRange, String> {

  @Override
  public void initialize(YearRange constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; // lets the @NotBlank annotation handle null values
    }
    if (!value.matches("\\d{4}")) {
      return false;
    }
    int year = Integer.parseInt(value);
    return year >= 1900 && year <= 2099;
  }
}
