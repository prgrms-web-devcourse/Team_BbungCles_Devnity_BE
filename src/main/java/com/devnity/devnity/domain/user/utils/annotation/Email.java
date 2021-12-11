package com.devnity.devnity.domain.user.utils.validator;

import com.devnity.devnity.common.error.exception.InvalidValueException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Email.EmailValidator.class)
public @interface Email {

  int min() default 10;
  int max() default 64;

  class EmailValidator implements ConstraintValidator<Email, String> {

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
        "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

      if (!Pattern.matches(regex, value)) {
        addConstraintViolation(context, "이메일 형식이 맞지 않습니다");
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
}
