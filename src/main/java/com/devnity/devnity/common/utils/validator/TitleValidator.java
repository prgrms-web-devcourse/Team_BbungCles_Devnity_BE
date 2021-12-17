package com.devnity.devnity.common.utils.validator;

import com.devnity.devnity.common.utils.annotation.Title;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<Title, String>{

  private int min;
  private int max;

  @Override
  public void initialize(Title constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (value == null || value.trim().isEmpty()) {
      addConstraintViolation(context, "제목을 입력해주세요");
      return false;
    }

    if (value.length() < min || value.length() > max) {
      String message = String.format("제목의 길이는 %d ~ %d입니다", min, max);
      addConstraintViolation(context, message);
      return false;
    }

    return false;
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
    //기본 메시지 비활성화
    context.disableDefaultConstraintViolation();
    //새로운 메시지 추가
    context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
  }
}
