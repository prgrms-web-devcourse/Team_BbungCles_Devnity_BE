package com.devnity.devnity.web.common.utils.validator;

import com.devnity.devnity.web.common.utils.annotation.UserName;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserName, String> {

  private int min;
  private int max;

  @Override
  public void initialize(UserName constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (Objects.isNull(value)) {
      addConstraintViolation(context, "이름을 입력해주세요");
      return false;
    }

    if (value.length() < min || value.length() > max) {
      addConstraintViolation(context, String.format("이름의 길이는 %d ~ %d 입니다.", min, max));
      return false;
    }

    final String regex =
      "^[가-힣a-zA-Z]+$";

    if (!Pattern.matches(regex, value)) {
      addConstraintViolation(context, "이름은 한글과 영문자만 가능합니다");
      return false;
    }

    return true;
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
    // 기본 메시지 비활성화
    context.disableDefaultConstraintViolation();
    // 새로운 메시지 추가
    context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
  }
}
