package com.devnity.devnity.web.common.utils.validator;

import com.devnity.devnity.web.common.utils.annotation.Email;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

  private int min;
  private int max;

  @Override
  public void initialize(Email constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (Objects.isNull(value)) {
      addConstraintViolation(context, "이메일을 입력해주세요");
      return false;
    }

    if (value.length() < min || value.length() > max) {
      addConstraintViolation(context, String.format("이메일의 길이는 %d ~ %d 입니다.", min, max));
      return false;
    }

    final String regex =
      "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    if (!Pattern.matches(regex, value)) {
      addConstraintViolation(context, "이메일 형식이 맞지 않습니다");
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
