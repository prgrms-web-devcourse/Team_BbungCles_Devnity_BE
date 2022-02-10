package com.devnity.devnity.web.common.utils.validator;

import com.devnity.devnity.web.common.utils.annotation.ApplicantLimit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ApplicantLimitValidator implements ConstraintValidator<ApplicantLimit, Integer> {

  private int min;
  private int max;

  @Override
  public void initialize(ApplicantLimit constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) {
      addConstraintViolation(context, "마감 인원을 입력해주세요.");
      return false;
    }

    if (value == 0) {
      addConstraintViolation(context, "마감 인원은 0명일 수 없습니다.");
      return false;
    }

    if (value < min || value > max) {
      addConstraintViolation(context, String.format("마감 인원은 최대 %d명, 최소 %d명입니다", min, max));
      return false;
    }

    return true;
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
  }
}
