package com.devnity.devnity.common.utils.validator;

import com.devnity.devnity.common.utils.annotation.Password;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  private int min;
  private int max;

  @Override
  public void initialize(Password constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (Objects.isNull(value)) {
      addConstraintViolation(context, "비밀번호를 입력해주세요");
      return false;
    }

    if (value.length() < min || value.length() > max) {
      addConstraintViolation(context, String.format("비밀번호의 길이는 %d ~ %d 입니다.", min, max));
      return false;
    }
    /**
     * ^ # start-of-string
     * (?=.*[0-9]) # a digit must occur at least once
     * (?=.*[a-z]) # a lower case letter must occur at least once
     * (?=.*[A-Z]) # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=]) # a special character must occur at least once
     * (?=\S+$) # no whitespace allowed in the entire string
     * .{8,} # anything, at least eight places though $ # end-of-string
     */
    final String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=])(?=\\S+$).*$";

    if (!Pattern.matches(regex, value)) {
      addConstraintViolation(context, "비밀번호는 숫자, 영문자, 특수문자(!@#$%^&+=)를 최소 1개씩 포함하며, 공백을 허용하지 않습니다");

      return false;
    }

    return true;
  }

  private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
    //기본 메시지 비활성화
    context.disableDefaultConstraintViolation();
    //새로운 메시지 추가
    context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
  }
}
