package com.devnity.devnity.web.common.utils.validator;

import com.devnity.devnity.web.common.utils.annotation.Comment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CommentValidator implements ConstraintValidator<Comment, String> {

  private int min;
  private int max;

  @Override
  public void initialize(Comment constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (value == null || value.trim().isEmpty()) {
      addConstraintViolation(context, "댓글을 입력해주세요");
      return false;
    }

    if (value.length() < min || value.length() > max) {
      String message = String.format("댓글의 길이는 %d ~ %d 입니다", min, max);
      addConstraintViolation(context, message);
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
